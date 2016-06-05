<form name="existingComponentForm" id="existingComponentForm">
			<!-- <input type="hidden" name="selectedItems" value="" id="splitCheckboxValue" > -->
			<table cellpadding="0" cellspacing="0" border="1" class="content-table border-simple colored-row tree-grid">
				<thead>
					<tr>
						<th width="10%"><label><input type="checkbox" id="select-all" /> <fmt:message key="splitgroup.screen.level.component.selectAll" /></label></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="StyleOrinNo" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.styleOrinNo" /></a></th>
						<th width="15%"><fmt:message key="splitgroup.screen.level.component.styleNo" /></th>
						<th width="15%"><a href="javascript:;" class="sortable" data-sort-column="productName" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.productName" /></a></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="colorCode" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.colorCode" /></a></th>
						<th width="10%"><fmt:message key="splitgroup.screen.level.component.defaultColor" /></th>
						<th width="10%"><a href="javascript:;" class="sortable" data-sort-column="alreadyInGroup" data-sorted-by=""><fmt:message key="splitgroup.screen.level.component.alreadyInGroup" /></a></th>
					</tr>
				</thead>
				<tbody class="row-container"></tbody>
			</table>
			</form>