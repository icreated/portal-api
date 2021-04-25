package co.icreated.portal.utils;

public class Misc {
	
	/**
	 * Check if variables are set
	 * @param strings
	 * @return
	 */
    public static boolean areSet(String... strings)
    {
        for(String s : strings) {
            if(s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }  

}
