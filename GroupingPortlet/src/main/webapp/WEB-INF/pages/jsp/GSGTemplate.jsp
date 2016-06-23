<form name="selectedComponentForm" id="selectedComponentForm">
			<input type="hidden" name="selectedItems" value="" id="splitCheckboxValue" >
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="10%"><label><input type="checkbox" class="select-all" /><fmt:message key="splitgroup.screen.level.component.selectAll" /></label></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.styleOrinNo" /></a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="vendorStyleNo" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.styleNo" /></a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.productName" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.colorCode" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorName" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.colorName" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="size" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.size" /></a></th>
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
				<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check" style="margin-left:7px"
					{{=row.alreadyInSameGroup == 'Yes' ? 'disabled="disabled"' : ''}} 
				/>								
			</td>
			<td class="text-center">{{=row.StyleOrinNo}}</td>
			<td class="text-center">{{=row.vendorStyleNo}}</td>
			<td>{{=row.productName}}</td>
			<td class="text-center">{{=row.colorCode}}</td>
			<td class="text-center">{{=row.colorName}}</td>
			<td class="text-center">{{=row.size}}</td>
		</tr>
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="7" align="center"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>