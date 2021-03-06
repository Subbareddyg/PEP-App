<form name="selectedComponentForm" id="selectedComponentForm">
			<input type="hidden" name="selectedItems" value="" id="splitCheckboxValue" >
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="8%"><label><input type="checkbox" class="select-all" /> Select All</label></th>
						<th width="12%"><a href="javascript:;" class="sortable" data-sort-column="orinNumber" data-sorted-by="">ORIN/Grouping#</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="vendorStyle" data-sorted-by="">Style Number</a></th>
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
	{{_.each(data, function(row){ }}
		{{ var key = Math.random().toString(); key= key.substring(key.indexOf('.')+1, 10) }}
		<tr>
			<td>
				<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check {{=row.isGroup != 'Y' ? 'styles' : 'groups'}}" style="margin-left:17px" 
					{{=((row.isGroup == 'Y' && 'CPG|SCG|SSG'.indexOf(row.entryType) < 0) || (row.isGroup == 'Y' && row.isSameGroup == 'Yes') || (row.isGroup != 'Y'  && (row.alreadyInSameGroup == 'Yes' || row.haveChildGroup == 'Y'))) ? 'disabled="disabled"' : ''}} data-item-type="{{=row.isGroup == 'Y' ? 'G' : 'S'}}" data-chknode-id="{{=(row.StyleOrinNo + '_' + key)}}" data-alreadyingroup="{{=row.alreadyInGroup}}" />
			</td>
			<td>
				{{ if(row.isGroup == 'Y'){ }}
					<div class="icon-tree parent-node-expand-ajax" data-level="1" data-parentnode-id="{{=row.StyleOrinNo}}" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}"  data-entry-type="{{=row.entryType}}">
						&nbsp;
					</div>
				{{ }else{ }}
					<div class="icon-tree parent-stylenode-expand-ajax" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}" data-parentStyleORIN="{{=row.StyleOrinNo}}" data-parentKey="{{=key}}">
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
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td>&nbsp;</td>
		<td colspan="5"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>
<script type="text/template" id="row-template-style-color">
{{ if(data && data.length){ }}
	{{ _.each(data, function(childRow, childKey){ }}
		<tr class="hidden-child" data-parent-id="{{=parentStyleORIN}}_{{=parentKey}}">
			<td><input type="checkbox" name="selectedChildItem_{{=parentKey}}" value="{{=childRow.StyleOrinNo}}" class="item-check" style="margin-left:24px"  {{=(childRow.alreadyInSameGroup == 'Yes' || childRow.haveChildGroup == 'Y') ? 'disabled="disabled"' : ''}} data-chkparent-id="{{=(parentStyleORIN + '_' + parentKey) }}" data-generator-id="{{=parentStyleORIN}}_{{=parentKey}}" data-item-type="SC" data-alreadyingroup="{{=childRow.alreadyInGroup}}" {{=(checkChildren !== undefined && checkChildren == true) ? 'checked="checked"' : ''  }} /></td>
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
{{ }else{ }}
	<tr class="hidden-child" data-parent-id="{{=parentStyleORIN}}_{{=parentKey}}">
		<td>&nbsp;</td>
		<td colspan="5"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>
<script type="text/template" id="row-template-group-children">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		{{ var random = Math.random().toString(); random= random.substring(random.indexOf('.')+1, 10) }}
		{{ key = random + '_' + key }}
		<tr class="dfd-children-{{=parentNode}} {{=(doShow !== undefined && doShow === false) ? 'hidden direct-' + parentNode : ''}}">
			<td><input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check group-style chk-level{{=level}}" data-parent-group="{{=parentNode}}" {{=(dataHeader.disableFlag == 'true' || row.alreadyInSameGroup == 'Yes') ? 'disabled="disabled"' : ''}} /></td>
			<td>
				{{ if(row.isGroup == 'Y'){ }}
					<div class="icon-tree parent-node-expand-ajax mar-level{{=level}}" data-level="2" data-parentnode-id="{{=row.StyleOrinNo}}" data-node-id="{{=(row.StyleOrinNo + '_' + key)}}" data-children-disable="{{=groupChildrenDisableFlag}}">
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
					<td><input type="checkbox" name="selectedChildItem_{{=key}}" data-parent-style="{{=row.StyleOrinNo}}" data-parent-group="{{=parentNode}}" value="{{=childRow.StyleOrinNo}}" class="item-check group-style-color chk-level{{=(level+1)}}" {{=(dataHeader.disableFlag == 'true' || childRow.alreadyInSameGroup == 'Yes') ? 'disabled="disabled"' : ''}} /></td>
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
	<tr class="dfd-children-{{=parentNode}}">
		<td>&nbsp;</td>
		<td colspan="5"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>