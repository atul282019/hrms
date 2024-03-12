package com.cotodel.hrms.web.util;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyUtility
{
	public static Logger logger = LoggerFactory.getLogger(CopyUtility.class);
	
	public static void copyProperties(Object objSrc, Object objDest, boolean DBYes){
		
		try{
			Class classSrc = objSrc.getClass();
			Method[] methSrcTemp = classSrc.getMethods();
			Field[] fieldSrc = classSrc.getFields();
			Class classDest = objDest.getClass();
			Method[] methDestTemp = classDest.getMethods();
			Field[] fieldDest = classDest.getFields();
			for(int i = 0; i < fieldSrc.length; i++){
				Field src = fieldSrc[i];
				if(src.getName().equalsIgnoreCase("serialVersionUID"))
					continue;
				for(int j = 0; j < fieldDest.length; j++){
					Field dest = fieldDest[j]; 
					if(dest.getName().equals(src.getName()) && 
							dest.getType().equals(src.getType())){
						dest.set(objDest, src.get(objSrc));
					}
				}
			}
			
			
			Method[] methSrc = new Method[methSrcTemp.length];
			int indexSrc = -1;
			for(int i = 0; i<methSrcTemp.length; i++){
				String name = methSrcTemp[i].getName();
				if(name.indexOf("get")==0 && 
					!name.equals("getClass") &&
					!name.equals("getServletWrapper") &&
					!name.equals("getMultipartRequestHandler"))
					methSrc[++indexSrc] = methSrcTemp[i];
			}
			
			int indexDest = -1;
			Method[] methDest = new Method[methDestTemp.length];
			for(int i = 0; i<methDestTemp.length; i++){
				String name = methDestTemp[i].getName();
				if(name.indexOf("set")==0 &&
					!name.equals("setMultipartRequestHandler") &&
					!name.equals("setServlet"))
					methDest[++indexDest] = methDestTemp[i];
			}

			logger.info("NO OF METHODS IN Src " + (indexSrc+1));
			logger.info("NO OF METHODS IN FORM " + (indexDest+1));
			for(int i = 0; i<=indexSrc; i++){
				String name = methSrc[i].getName();
				if(name.indexOf("get")==0){
					Object[] obj = null;
					Object sOut = null;
					try{
						sOut = (Object)methSrc[i].invoke(objSrc, obj);
					}catch(IllegalArgumentException ile){
						logger.error("Couldn't invoke " + name);
					}
					for(int j = 0; j<=indexDest; j ++){
						String nameDest = methDest[j].getName();
						String newMeth = "s" + name.substring(1);
						String newMethDB = newMeth+"_db";
						if(newMeth.equals(nameDest)) {
							Object[] obj1 = {sOut};
							try{
								methDest[j].invoke(objDest, obj1);
							}catch(IllegalArgumentException ile){
								logger.error("Couldn't invoke " + nameDest);
							}
							if(!DBYes)
								break;
						}
						if(DBYes && newMethDB.equals(nameDest)){
							Object[] obj1 = {sOut};
							try{
								methDest[j].invoke(objDest, obj1);
							}catch(IllegalArgumentException ile){
								logger.error("Couldn't invoke " + nameDest);
							}
							break;
						}
					}
				}
			}
		}catch(Exception e){
			logger.info("CopyUtility failed");
			e.printStackTrace();
		}
	}

	public static void copyProperties(Object objSrc, Object objDest){
		copyProperties(objSrc, objDest, false);
	}
	
}