package com.mallotore
 
import java.util.ArrayList; 
 
import org.hyperic.sigar.Sigar; 
import org.hyperic.sigar.SigarException; 
import org.hyperic.sigar.SigarProxy
import org.hyperic.sigar.cmd.Df
import org.hyperic.sigar.cmd.Shell
import org.hyperic.sigar.pager.PageFetchException
import org.hyperic.sigar.pager.StaticPageFetcher
import org.hyperic.sigar.shell.CollectionCompleter
import org.hyperic.sigar.FileSystem; 
import org.hyperic.sigar.FileSystemMap; 
import org.hyperic.sigar.FileSystemUsage; 
import org.hyperic.sigar.NfsFileSystem; 
 
import org.hyperic.sigar.shell.FileCompleter; 
import org.hyperic.sigar.shell.ProcessQueryCompleter
import org.hyperic.sigar.shell.ShellCommandExecException
import org.hyperic.sigar.shell.ShellCommandUsageException
import org.hyperic.sigar.util.GetlineCompleter; 
import org.hyperic.sigar.util.PrintfFormat
 
/**
 * Report filesytem disk space usage. 
 */ 
class Monitor implements GetlineCompleter { 
 
    
    
    private Shell shell; 
    private PrintStream out = System.out; 
    private PrintStream err = System.err; 
    private Sigar sigar; 
    private SigarProxy proxy; 
    private List output = new ArrayList(); 
    //private CollectionCompleter completer; 
    private GetlineCompleter ptqlCompleter; 
    private Collection completions = new ArrayList(); 
    private PrintfFormat formatter; 
    private ArrayList printfItems = new ArrayList();  
 
    private static final String OUTPUT_FORMAT = 
        "%-15s %4s %4s %5s %4s %-15s %s"; 
 
    //like df -h -a 
    private static final String[] HEADER = [
        "Filesystem", 
        "Size", 
        "Used", 
        "Avail", 
        "Use%", 
        "Mounted on", 
        "Type" 
    ]
    //df -i 
    private static final String[] IHEADER = [
        "Filesystem", 
        "Inodes", 
        "IUsed", 
        "IFree", 
        "IUse%", 
        "Mounted on", 
        "Type" 
    ]
 
    private GetlineCompleter completer; 
    private boolean opt_i; 

    public Df(Shell shell) { 
        this.shell = shell; 
        this.out   = shell.getOutStream(); 
        this.err   = shell.getErrStream(); 
        this.sigar = shell.getSigar(); 
        this.proxy = shell.getSigarProxy(); 
         
        //provide simple way for handlers to implement tab completion 
        this.completer = new CollectionCompleter(shell); 
        if (isPidCompleter()) { 
            this.ptqlCompleter = new ProcessQueryCompleter(shell); 
        }  
        setOutputFormat(OUTPUT_FORMAT); 
        this.completer = new FileCompleter(shell); 
    }
 
    public Df() { 
        this.shell = new Shell(); 
        this.out   = shell.getOutStream(); 
        this.err   = shell.getErrStream(); 
        this.sigar = shell.getSigar(); 
        this.proxy = shell.getSigarProxy(); 
         
        //provide simple way for handlers to implement tab completion 
        this.completer = new CollectionCompleter(shell); 
        if (isPidCompleter()) { 
            this.ptqlCompleter = new ProcessQueryCompleter(shell); 
        }
        shell.setPageSize(PageControl.SIZE_UNLIMITED);
        setOutputFormat(OUTPUT_FORMAT); 
    } 
    
    public void setOutputFormat(String format) { 
        this.formatter = new PrintfFormat(format); 
    } 
 
    public PrintfFormat getFormatter() { 
        return this.formatter; 
    } 
 
    public String sprintf(String format, Object[] items) { 
        return new PrintfFormat(format).sprintf(items); 
    } 
 
    public void printf(String format, Object[] items) { 
        println(sprintf(format, items)); 
    } 
 
    public void printf(Object[] items) { 
        PrintfFormat formatter = getFormatter(); 
        if (formatter == null) { 
            //see flushPrintfItems 
            this.printfItems.add(items); 
        } 
        else { 
            println(formatter.sprintf(items)); 
        } 
    } 
 
    public void printf(List items) { 
        printf((Object[])items.toArray(new Object[0])); 
    } 
 
    public void println(String line) { 
        if (this.shell.isInteractive()) { 
            this.output.add(line); 
        } 
        else { 
            this.out.println(line); 
        } 
    } 
 
    private void flushPrintfItems() { 
        if (this.printfItems.size() == 0) { 
            return; 
        } 
 
        //no format was specified, just line up the columns 
        int[] max = null; 
 
        for (Iterator it=this.printfItems.iterator(); 
             it.hasNext();) 
        { 
            Object[] items = (Object[])it.next(); 
            if (max == null) { 
                max = new int[items.length]; 
                Arrays.fill(max, 0); 
            } 
            for (int i=0; i<items.length; i++) { 
                int len = items[i].toString().length(); 
                if (len > max[i]) { 
                    max[i] = len; 
                } 
            } 
        } 
 
        StringBuffer format = new StringBuffer(); 
        for (int i=0; i<max.length; i++) { 
            format.append("%-" + max[i] + "s"); 
            if (i < max.length-1) { 
                format.append("    "); 
            } 
        } 
 
        for (Iterator it=this.printfItems.iterator(); 
             it.hasNext();) 
        { 
            printf(format.toString(), (Object[])it.next()); 
        } 
        this.printfItems.clear(); 
    } 
 
    public void flush() { 
        flushPrintfItems(); 
 
        try { 
            this.shell.performPaging(new StaticPageFetcher(this.output)); 
        } catch(PageFetchException e) { 
            this.err.println("Error paging: " + e.getMessage()); 
        } finally { 
            this.output.clear(); 
        } 
    } 
 
    public boolean validateArgs(String[] args) { 
        return args.length == 0; 
    } 
 
    public void processCommand(String[] args)  
        throws ShellCommandUsageException, ShellCommandExecException  
    { 
        if (!validateArgs(args)) { 
            throw new ShellCommandUsageException(getSyntax()); 
        } 
 
        try { 
            output(args); 
        } catch (SigarException e) { 
            throw new ShellCommandExecException(e.getMessage()); 
        } 
    } 
 
    public Collection getCompletions() { 
        return this.completions; 
    } 
 
    public boolean isPidCompleter() { 
        return false; 
    } 
 
    public String completePid(String line) { 
        if ((line.length() >= 1) && 
            Character.isDigit(line.charAt(0))) 
        { 
            return line; 
        } 
 
        return this.ptqlCompleter.complete(line); 
    } 
 
    public String complete(String line) { 
        if (isPidCompleter()) { 
            return completePid(line); 
        } 
        GetlineCompleter c = getCompleter(); 
        if (c != null) { 
            return c.complete(line); 
        } 
 
        this.completer.setCollection(getCompletions()); 
        return this.completer.complete(line); 
    } 
 
    public GetlineCompleter getCompleter() { 
        return this.completer; 
    }
 
    public String getSyntaxArgs() { 
        return "[filesystem]"; 
    } 
 
    public String getUsageShort() { 
        return "Report filesystem disk space usage"; 
    } 
 
    public void printHeader() { 
        printf(this.opt_i ? IHEADER : HEADER); 
    } 
 
    public void output(String[] args) throws SigarException { 
        this.opt_i = false; 
        ArrayList sys = new ArrayList(); 
 
        if (args.length > 0) { 
            FileSystemMap mounts = this.proxy.getFileSystemMap(); 
            for (int i=0; i<args.length; i++) { 
                String arg = args[i]; 
                if (arg.equals("-i")) { 
                    this.opt_i = true; 
                    continue; 
                } 
                String name = FileCompleter.expand(arg); 
                FileSystem fs = mounts.getMountPoint(name); 
 
                if (fs == null) { 
                    throw new SigarException(arg + 
                                             " No such file or directory"); 
                } 
                sys.add(fs); 
            } 
        } 
        if (sys.size() == 0) { 
            FileSystem[] fslist = this.proxy.getFileSystemList(); 
            for (int i=0; i<fslist.length; i++) { 
                sys.add(fslist[i]); 
            } 
        } 
 
        printHeader(); 
        for (int i=0; i<sys.size(); i++) { 
            output((FileSystem)sys.get(i)); 
        } 
    } 
 
    public void output(FileSystem fs) throws SigarException { 
        long used, avail, total, pct; 
 
        try { 
            FileSystemUsage usage; 
            if (fs instanceof NfsFileSystem) { 
                NfsFileSystem nfs = (NfsFileSystem)fs; 
                if (!nfs.ping()) { 
                    println(nfs.getUnreachableMessage()); 
                    return; 
                } 
            } 
            usage = this.sigar.getFileSystemUsage(fs.getDirName()); 
            if (this.opt_i) { 
                used  = usage.getFiles() - usage.getFreeFiles(); 
                avail = usage.getFreeFiles(); 
                total = usage.getFiles(); 
                if (total == 0) { 
                    pct = 0; 
                } 
                else { 
                    long u100 = used * 100; 
                    pct = u100 / total + 
                        ((u100 % total != 0) ? 1 : 0); 
                } 
            } 
            else { 
                used = usage.getTotal() - usage.getFree(); 
                avail = usage.getAvail(); 
                total = usage.getTotal(); 
 
                pct = (long)(usage.getUsePercent() * 100); 
            } 
        } catch (SigarException e) { 
            //e.g. on win32 D:\ fails with "Device not ready" 
            //if there is no cd in the drive. 
            used = avail = total = pct = 0; 
        } 
 
        String usePct; 
        if (pct == 0) { 
            usePct = "-"; 
        } 
        else { 
            usePct = pct + "%"; 
        } 
         
        ArrayList items = new ArrayList(); 
 
        items.add(fs.getDevName()); 
        items.add(formatSize(total)); 
        items.add(formatSize(used)); 
        items.add(formatSize(avail)); 
        items.add(usePct); 
        items.add(fs.getDirName()); 
        items.add(fs.getSysTypeName() + "/" + fs.getTypeName()); 
 
        printf(items); 
    } 
 
    private String formatSize(long size) { 
        return this.opt_i ? String.valueOf(size) : Sigar.formatSize(size * 1024); 
    } 
 
    public static void main(String[] args) throws Exception { 
        new Df().processCommand(args); 
    } 
}
