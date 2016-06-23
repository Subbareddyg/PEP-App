<form name="selectedComponentForm" id="selectedComponentForm">
			<input type="hidden" name="selectedItems" value="" id="splitCheckboxValue" >
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="8%"><label><input type="checkbox" class="select-all" /> Select All</label></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by="">ORIN#</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="vendorStyleNo" data-sorted-by="">Style Number</a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by="">Name</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by="">Color</a></th>
						<th width="8%"><a href="javascript:;" class="sortable" data-sort-column="alreadyInGroup" data-sorted-by="">Already In Group</a></th>
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
					{{=row.alreadyInSameGroup == 'Yes' ? 'disabled="disabled"' : ''}} />
			</td>
			<td>
				{{ if(row.childList && row.childList.length){ }}
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
			<td class="text-center">{{=row.alreadyInGroup}}</td>			
		</tr>
		{{ if(row.childList && row.childList.length){ }}
			{{ _.each(row.childList, function(childRow, childKey){ }}
				<tr class="hidden-child" data-parent-id="{{=(row.StyleOrinNo + '_' + key) }}">
					<td>&nbsp;</td>
					<td>
						<div class="icon-tree leaf-node" data-node-id="{{=(childRow.StyleOrinNo + '_' + childKey)}}">
						&nbsp;
						</div> {{=childRow.StyleOrinNo}}
					</td>
					<td class="text-center">{{=childRow.vendorStyleNo}}</td>
					<td>{{=childRow.productName}}</td>
					<td class="text-center">{{=childRow.colorName}}</td>
					<td class="text-center">{{=row.alreadyInGroup}}</td>
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