package pl.gihon.fdd.poi.web;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class AssignPlaceToAreaForm {

	private int areaNr;
	private String placeIds;

	public void setPlaceIds(String placeIds) {
		this.placeIds = placeIds;
	}

	public int getAreaNr() {
		return areaNr;
	}

	public void setAreaNr(int areaNr) {
		this.areaNr = areaNr;
	}

	public String getPlaceIds() {
		return placeIds;
	}

	public List<Integer> getPlaceIdNumbers() {
		List<String> placeIdsStr = Lists.newArrayList(placeIds.split(","));
		List<Integer> ids = placeIdsStr.stream().map(Integer::parseInt).collect(Collectors.toList());
		return ids;
	}

	@Override
	public String toString() {
		return "AssignPlaceToAreaForm [areaNr=" + areaNr + ", placeIds=" + placeIds + "]";
	}

}
