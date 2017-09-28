package br.edu.ifam.socialdesk.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class ModelMapperUtil {

	public static <D, S> D map(S souce, Class<D> destinationType, PropertyMap<S, D> propertyMap) {
		ModelMapper mapper = new ModelMapper();
		if (propertyMap != null) {
			mapper.addMappings(propertyMap);
		}
		return mapper.map(souce, destinationType);
	}
	
	public static <D, S> List<D> map(List<S> listSource, Class<D> clazzDestination) {
		List<D> listDestination = new ArrayList<D>();
		for (S source : listSource) {
			ModelMapper mapper = new ModelMapper();
			
			mapper.addMappings(new PropertyMap<S, D>() {
				@Override
				protected void configure() {
					
				}
			});
			
			listDestination.add(mapper.map(source, clazzDestination));
		}
		
		return listDestination;
	}
	
	public static <D, S> List<D> map(List<S> listSource, Class<D> clazzDestination, PropertyMap<S, D> propertyMap) {
		List<D> listDestination = new ArrayList<D>();
		for (S source : listSource) {
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(propertyMap);
			listDestination.add(mapper.map(source, clazzDestination));
		}
		
		return listDestination;
	}
	
}
