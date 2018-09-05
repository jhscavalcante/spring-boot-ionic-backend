package com.nelioalves.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class URL {

	public static List<Integer> decodeIntList(String s){
		/* Solução 1 - Tradicional */
		/*
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		
		for(int i=0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		*/
		
		/* Solução 2 usando LAMBDA */
		
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
