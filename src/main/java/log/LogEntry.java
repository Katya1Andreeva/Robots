package log;

public class LogEntry
{
    private LogLevel logLevel;
    private String strMessage;

    private int numberMessage;
    
    public LogEntry(LogLevel logLevel, String strMessage, int numberMessage)
    {
        this.strMessage = strMessage;
        this.logLevel = logLevel;
        this.numberMessage = numberMessage;
    }
    
    public String getMessage()
    {
        return strMessage;
    }
    
    public LogLevel getLevel()
    {
        return logLevel;
    }

    public int getNumberMessage(){return numberMessage;}
}

