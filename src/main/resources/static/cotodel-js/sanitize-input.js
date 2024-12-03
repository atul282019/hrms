function escapeRegex(string) {
		    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
		}
		const sqlKeywords = ["SELECT", "INSERT", "DELETE", "UPDATE", "DROP", "ALTER", "CREATE", 
		          "WHERE", "FROM", "JOIN", "UNION", "ORDER", "GROUP", "EXEC", "--",  ";", "/*", "*/"];
		
	function sanitizeInput(inputElement) {
            let userInput = inputElement.value;
            
            // Remove keywords
            sqlKeywords.forEach(keyword => {
				const escapedKeyword = escapeRegex(keyword); // Escape special characters
			    const regex = new RegExp(`\\b${escapedKeyword}\\b`, "gi"); // Create safe regex
	    	    userInput = userInput.replace(regex, ""); // Replace the keyword
            });
            
        inputElement.value = userInput;
}