<form name="existingComponentForm" id="existingComponentForm">
			 <input type="hidden" name="componentList" value="" id="existingSeletedItems" > 
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="10%"><label><input type="checkbox" class="select-all" <c:if test="${readonly =='yes'}"> disabled="disabled" </c:if>  /><fmt:message key="splitgroup.screen.level.component.selectAll" /></label></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.styleOrinNo" /></a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="vendorStyleNo" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.styleNo" /></a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.productName" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.colorCode" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorName" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.colorName" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="size" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.size" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="priority" data-sorted-by="">Priority</a></th>
					</tr>
				</thead>
				<tbody class="row-container"></tbody>
			</table>
			</form>

<!-- Component Table Row Template starts -->
<script type="text/template" id="existing-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		<tr>
			<td>
				<c:if test="${readonly =='yes'}"> 
				<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check" style="margin-left:14px" disabled="disabled" /> 
				</c:if>
				<c:if test="${readonly !='yes'}"> 
				<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check" style="margin-left:14px" /> 
				</c:if>
			</td>
			<td class="text-center">{{=row.StyleOrinNo}}</td>
			<td class="text-center">{{=row.vendorStyleNo}}</td>
			<td>{{=row.productName}}</td>
			<td class="text-center">{{=row.colorCode}}</td>
			<td class="text-center">{{=row.colorName}}</td>
			<td class="text-center">{{=row.size}}</td>
			<td class="text-center">
				<c:if test="${readonly !='yes'}">
					<input type="number" class="tree" value="{{=row.priority}}" name="{{=row.StyleOrinNo}}_{{=key}}" min="1" />
				</c:if>
				<c:if test="${readonly =='yes'}">
					<input type="number" class="tree" value="{{=row.priority}}" name="{{=row.StyleOrinNo}}_{{=key}}" min="1" disabled="disabled"/>
				</c:if>
			</td>
		</tr>
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="8" align="center"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>