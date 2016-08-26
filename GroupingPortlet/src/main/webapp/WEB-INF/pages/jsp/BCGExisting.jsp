<form name="existingComponentForm" id="existingComponentForm">
			<input type="hidden" name="componentList" value="" id="existingSeletedItems">
			<input type="hidden" name="BCGDefaultColor" value="" id="BCGDefaultColor">
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="9%"><label><input type="checkbox" class="select-all" <c:if test="${readonly =='yes'}"> disabled="disabled" </c:if> /> Select All </label></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by="">ORIN/Grouping#</a></th>
						<th width="12%"><a href="javascript:;" class="sortable" data-sort-column="vendorStyleNo" data-sorted-by="">Style Number</a></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by="">Name</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by="">Color Code</a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorName" data-sorted-by="">Color Name</a></th>
						<th width="8%"><a href="javascript:;" class="sortable" data-sort-column="priority" data-sorted-by="">Priority</a></th>
						<th width="10%">Default Color</th>
					</tr>
				</thead>
				<tbody class="row-container"></tbody>
			</table>
			</form>
<!-- Component Table Row Template starts -->
<script type="text/template" id="existing-template">
{{ if(data.length){ }}
	{{_.each(data, function(row, key){ }}
		{{ key = 'E'+key }}
		<tr>
			<td>
				<c:if test="${readonly !='yes'}">
					<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check" style="margin-left:12px" />
				</c:if>
				<c:if test="${readonly =='yes'}">
					<input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check" style="margin-left:12px" disabled="disabed" />
				</c:if>
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
			<td class="text-center">
				<c:if test="${readonly !='yes'}">
					<input type="number" class="tree" value="{{=row.priority}}" name="{{=row.StyleOrinNo}}_{{=key}}" min="1" />
				</c:if>
				<c:if test="${readonly =='yes'}">
					<input type="number" class="tree" value="{{=row.priority}}" name="{{=row.StyleOrinNo}}_{{=key}}" min="1" disabled="disabled"/>
				</c:if>
			</td>
			<td class="text-center">
				&nbsp;
			</td>
		</tr>
		{{ if(row.childList && row.childList.length){ }}
			{{ _.each(row.childList, function(childRow, childKey){ }}
				<tr class="hidden-child" data-parent-id="{{=(row.StyleOrinNo + '_' + key) }}">
					<td>
						<input type="checkbox" name="selectedChildItem_{{=key}}" value="{{=childRow.StyleOrinNo}}" class="item-check" style="margin-left:24px"disabled="disabled"  />
					</td>
					<td>
						<div class="icon-tree leaf-node" data-node-id="{{=(childRow.StyleOrinNo + '_' + childKey)}}">
						&nbsp;
						</div> {{=childRow.StyleOrinNo}}
					</td>
					<td class="text-center">{{=childRow.vendorStyleNo}}</td>
					<td>{{=childRow.productName}}</td>
					<td class="text-center">{{=childRow.colorCode}}</td>
					<td class="text-center">{{=childRow.colorName}}</td>
					<td class="text-center">&nbsp;</td>
					<td class="text-center">
						<c:if test="${readonly =='yes'}">  
						<input type="radio" name="{{=row.StyleOrinNo}}_defaultColor" value="{{=childRow.StyleOrinNo}}_{{=childRow.colorCode}}" {{=childRow.defaultColor == 'true' ? 'checked="checked"' : ''}} disabled="disabled" />
						</c:if>
						<c:if test="${readonly !='yes'}">  
						<input type="radio" name="{{=row.StyleOrinNo}}_defaultColor" class="trueDefult" value="{{=childRow.StyleOrinNo}}_{{=childRow.colorCode}}" {{=childRow.defaultColor == 'true' ? 'checked="checked"' : ''}} />
						</c:if>
					</td>
				</tr>
			{{ }) }}
		{{ } }}
	{{ }) }}	
{{ }else{ }}
	<tr>
		<td colspan="8" align="center"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>
<script type="text/template" id="existing-template-group-children">
{{ if(data.length){ }}
	{{ var parentCount = 0 }}
	{{_.each(data, function(row, key){ }}
		{{ var random = Math.random().toString(); random= random.substring(random.indexOf('.')+1, 10) }}
		{{ key = random + '_' + key }}
		{{ if(row.existInGroup == 'Yes'){ }}
			<tr class="dfd-children-{{=parentNode}}">
				<td><input type="checkbox" name="selectedItem[]" value="{{=row.StyleOrinNo}}" class="item-check chk-level{{=level}} group-style" disabled="disabled" /></td>
				<td>
					{{ if(row.childList && row.childList.length){ }}
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
				<td class="text-center">&nbsp;</td>			
				<td class="text-center">&nbsp;</td>			
			</tr>	
			{{ if(row.childList && row.childList.length){ }}
				{{ var childCount = 0; }}
				{{ _.each(row.childList, function(childRow, childKey){ }}
					{{ var random = Math.random().toString(); random= random.substring(random.indexOf('.')+1, 10) }}
					{{ childKey = random + '_' + childKey }}
					{{ if(childRow.existInGroup == 'Yes'){ }}
						<tr class="dfd-children-{{=parentNode}} hidden-child" data-parent-id="{{=(row.StyleOrinNo + '_' + key) }}">
							<td><input type="checkbox" name="selectedChildItem_{{=key}}" data-parent-style="{{=row.StyleOrinNo}}" data-parent-group="{{=parentNode}}" value="{{=childRow.StyleOrinNo}}" class="item-check group-style-color chk-level{{=(level+1)}}" disabled="disabled" /></td>
							<td>
								<div class="icon-tree leaf-node pad-level{{=level}}" data-node-id="{{=(childRow.StyleOrinNo + '_' + childKey)}}">
								&nbsp;
								</div> {{=childRow.StyleOrinNo}}
							</td>
							<td class="text-center">{{=childRow.vendorStyleNo}}</td>
							<td>{{=childRow.productName}}</td>
							<td class="text-center">{{=childRow.colorCode}}</td>
							<td class="text-center">{{=childRow.colorName}}</td>
							<td class="text-center">&nbsp;</td>
							<td class="text-center">
								<c:if test="${readonly =='yes'}">  
								<input type="radio" name="{{=(parentNode.indexOf('_') >= 0) ? parentNode.split('_')[0] : parentNode}}_defaultColor" value="{{=parentNode}}_{{=childRow.StyleOrinNo}}_{{=childRow.colorCode}}" {{=childRow.defaultColor == 'true' ? 'checked="checked"' : ''}} disabled="disabled" />
								</c:if>
								<c:if test="${readonly !='yes'}">  
								<input type="radio" name="{{=(parentNode.indexOf('_') >= 0) ? parentNode.split('_')[0] : parentNode}}_defaultColor" class="trueDefult" value="{{=(parentNode.indexOf('_') >= 0) ? parentNode.split('_')[0] : parentNode}}_{{=childRow.StyleOrinNo}}_{{=childRow.colorCode}}" {{=childRow.defaultColor == 'true' ? 'checked="checked"' : ''}} />
								</c:if>
							</td>
						</tr>
						{{ childCount++; }}
					{{ } }}
				{{ }) }}
				{{ if(!childCount){ }}
					<tr class="dfd-children-{{=parentNode}} hidden-child" data-parent-id="{{=(row.StyleOrinNo + '_' + key) }}">
						<td>&nbsp;</td>
						<td colspan="7"><strong>No record Found!</strong></td>
					</tr>
				{{ } }}
			{{ } }}
			{{ parentCount++ }}
		{{ } }}
	{{ }) }}
	{{ if(!parentCount){ }}
		<tr class="dfd-children-{{=parentNode}}">
			<td>&nbsp;</td>
			<td colspan="7"><strong>No record Found!</strong></td>
		</tr>
	{{ } }}
{{ }else{ }}
	<tr class="dfd-children">
		<td>&nbsp;</td>
		<td colspan="7"><strong>{{=dataHeader.message ? dataHeader.message : 'No record Found!'}}</strong></td>
	</tr>
{{ } }}
</script>