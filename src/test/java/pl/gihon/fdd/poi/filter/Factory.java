package pl.gihon.fdd.poi.filter;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.FactoryBean;

public class Factory implements FactoryBean<List<PredicateForFilter>> {

	@Override
	public List<PredicateForFilter> getObject() throws Exception {
		return Lists.newArrayList(new FilterMustNotLangs("", ""));
	}

	@Override
	public Class<?> getObjectType() {
		return List.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
