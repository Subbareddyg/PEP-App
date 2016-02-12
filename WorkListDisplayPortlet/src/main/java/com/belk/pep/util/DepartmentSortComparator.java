package com.belk.pep.util;

import java.util.Comparator;

import com.belk.pep.domain.PepDepartment;

public class DepartmentSortComparator implements Comparator<PepDepartment> {

	@Override
	public int compare(PepDepartment o1, PepDepartment o2) {
		Integer id1 = Integer.parseInt(o1.getId().getDeptId());
		Integer id2 = Integer.parseInt(o2.getId().getDeptId());

		
		return id1.compareTo(id2);
	}

}
