<form name="selectedComponentForm" id="selectedComponentForm">
			<input type="hidden" name="selectedItems" value="" id="splitCheckboxValue" >
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="8%"><label><input type="checkbox" class="select-all" /> Select All</label></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by="">ORIN/Grouping#</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="vendorStyleNo" data-sorted-by="">Style Number</a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by="">Name</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by="">Color Code</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorName" data-sorted-by="">Color Name</a></th>
					</tr>
				</thead>
				<tbody class="row-container"></tbody>
			</table>
			</form>
<!-- Component Table Row Template starts -->
<script type="text/template" id="row-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		<tr>
			<td>
				<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check" style="margin-left:17px" 
					{{=(row.alreadyInSameGroup == 'Yes' || row.haveChildGroup == 'Y') ? 'disabled="disabled"' : ''}} data-item-type="{{=row.isGroup == 'Y' ? 'G' : 'S'}}"}} data-chknode-id="{{=(row.StyleOrinNo + '_' + key)}}" />
			</td>
			<td>
				{{ if(row.isGroup == 'Y'){ }}
					<div class="icon-tree parent-node-expand-ajax" data-level="1" data-parentnode-id="{{=row.StyleOrinNo}}" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}">
						&nbsp;
					</div>
				{{ }else if(row.childList && row.childList.length){ }}
					<div class="icon-tree parent-node-expand" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}">
						&nbsp;
					</div>
				{{ }else{ }}
					<div class="icon-tree parent-leaf" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}">
						&nbsp;
					</div>
				{{ } }}
				{{=row.StyleOrinNo}}
			</td>
			<td class="text-center">{{=row.vendorStyleNo}}</td>
			<td>{{=row.productName}}</td>
			<td class="text-center">{{=row.colorCode}}</td>			
			<td class="text-center">{{=row.colorName}}</td>	
		</tr>
		{{ if(row.childList && row.childList.length){ }}
			{{ _.each(row.childList, function(childRow, childKey){ }}
				<tr class="hidden-child" data-parent-id="{{=(row.StyleOrinNo + '_' + key) }}">
					<td><input type="checkbox" name="selectedChildItem_{{=key}}" value="{{=childRow.StyleOrinNo}}" class="item-check" style="margin-left:24px"  {{=(childRow.alreadyInSameGroup == 'Yes' || childRow.haveChildGroup == 'Y') ? 'disabled="disabled"' : ''}} data-chkparent-id="{{=(row.StyleOrinNo + '_' + key) }}" data-item-type="SC" /></td>
					<td>
						<div class="icon-tree leaf-node" data-node-id="{{=(childRow.StyleOrinNo + '_' + childKey)}}">
						&nbsp;
						</div> {{=childRow.StyleOrinNo}}
					</td>
					<td class="text-center">{{=childRow.vendorStyleNo}}</td>
					<td>{{=childRow.productName}}</td>
					<td class="text-center">{{=childRow.colorCode}}</td>			
					<td class="text-center">{{=childRow.colorName}}</td>	
				</tr>
			{{ }) }}
		{{ } }}
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="6" align="center"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>
<script type="text/template" id="row-template-group-children">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		{{ var random = Math.random().toString(); random= random.substring(random.indexOf('.')+1, 10) }}
		{{ key = random + '_' + key }}
		<tr class="dfd-children-{{=parentNode}}">
			<td><input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check chk-level{{=level}}" disabled="disabled" /></td>
			<td>
				{{ if(row.isGroup == 'Y'){ }}
					<div class="icon-tree parent-node-expand-ajax mar-level{{=level}}" data-level="2" data-parentnode-id="{{=row.StyleOrinNo}}" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}">
						&nbsp;
					</div>
				{{ }else if(row.childList && row.childList.length){ }}
					<div class="icon-tree parent-node-expand mar-level{{=level}}" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}">
						&nbsp;
					</div>
				{{ }else{ }}
					<div class="icon-tree parent-leaf mar-level{{=level}}" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}">
						&nbsp;
					</div>
				{{ } }}
				{{=row.StyleOrinNo}}
			</td>
			<td class="text-center">{{=row.vendorStyleNo}}</td>
			<td>{{=row.productName}}</td>
			<td class="text-center">{{=row.colorCode}}</td>			
			<td class="text-center">{{=row.colorName}}</td>
		</tr>
		{{ if(row.childList && row.childList.length){ }}
			{{ _.each(row.childList, function(childRow, childKey){ }}
				{{ var random = Math.random().toString(); random= random.substring(random.indexOf('.')+1, 10) }}
				{{ childKey = random + '_' + childKey }}
				<tr class="dfd-children-{{=parentNode}} hidden-child" data-parent-id="{{=(row.StyleOrinNo + '_' + key) }}">
					<td><input type="checkbox" name="selectedChildItem_{{=key}}" value="{{=childRow.StyleOrinNo}}" class="item-check chk-level{{=(level+1)}}"  disabled="disabled"/></td>
					<td>
						<div class="icon-tree leaf-node pad-level{{=level}}" data-node-id="{{=(childRow.StyleOrinNo + '_' + childKey)}}">
						&nbsp;
						</div> {{=childRow.StyleOrinNo}}
					</td>
					<td class="text-center">{{=childRow.vendorStyleNo}}</td>
					<td>{{=childRow.productName}}</td>
					<td class="text-center">{{=childRow.colorCode}}</td>			
					<td class="text-center">{{=childRow.colorName}}</td>
				</tr>
			{{ }) }}
		{{ } }}
	{{ }) }}	
{{ }else{ }}
	<tr class="dfd-children">
		<td colspan="6" align="center"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>