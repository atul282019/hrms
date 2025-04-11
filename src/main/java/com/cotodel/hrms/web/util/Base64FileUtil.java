package com.cotodel.hrms.web.util;

public class Base64FileUtil {

	public static String getFileExtensionFromBase64(String base64String) {
        if (base64String == null || !base64String.contains("data:")) {
            return null;
        }

        try {
            String mimeType = base64String.substring(base64String.indexOf(":") + 1, base64String.indexOf(";"));
            switch (mimeType) {
                case "image/jpeg": return "jpg";
                case "image/png": return "png";
                case "image/gif": return "gif";
                case "application/pdf": return "pdf";
                case "text/plain": return "txt";
                case "application/msword": return "doc";
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": return "docx";
                case "application/vnd.ms-excel": return "xls";
                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": return "xlsx";
                // Add more MIME types as needed
                default: return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
	
}
