import java.lang.reflect.Field
import grails.util.GrailsUtil

class BootStrap {

    def init = { servletContext ->

    	def appHome = servletContext.getRealPath("/")
        try {
            rxtxBoostrap(appHome)
        } catch (Exception e) {
            log.error ("Failed to intitialize temperatureReaderService: ${e.getMessage()}", e)
        }
    }
    def destroy = {
    }

    private rxtxBoostrap(appHomePath) {
        if (GrailsUtil.environment != "production"){
            def rxtxLib = new File(appHomePath, "../ext/rxtx/Current").canonicalPath
            try {
                addDirToJavaLibraryPathAtRuntime(rxtxLib)
            } catch (Exception e) {
                log.error("Error adding the RXTX libraries to java.library.path: ${e.message}")
            }
        }
    }
    
    private addDirToJavaLibraryPathAtRuntime(String libraryDirPath) {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths")
            field.setAccessible(true)
            String[] paths = (String[])field.get(null)
            for (int i = 0; i < paths.length; i++) {
                if (libraryDirPath.equals(paths[i])) {
                    return
                }
            }
            String[] tmp = new String[paths.length+1]
            System.arraycopy(paths,0,tmp,0,paths.length)
            tmp[paths.length] = libraryDirPath
            field.set(null,tmp)
            def javaLib = "java.library.path"
            System.setProperty(javaLib, System.getProperty(javaLib) + File.pathSeparator + libraryDirPath)
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path to ${libraryDirPath}")
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path to ${libraryDirPath}")
        }
    }
}
