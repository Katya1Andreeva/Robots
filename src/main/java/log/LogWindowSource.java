package log;

import java.util.ArrayList;
import java.util.Collections;

public class LogWindowSource
{
    private int iQueueLength;
    private LimitedBlockingDeque<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;
    
    public LogWindowSource(int iQueueLength) 
    {
        this.iQueueLength = iQueueLength;
        this.messages = new LimitedBlockingDeque<LogEntry>(iQueueLength);
        this.listeners = new ArrayList<LogChangeListener>();
    }
    
    public void registerListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.add(listener);
            activeListeners = null;
        }
    }
    
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.remove(listener);
            activeListeners = null;
        }
    }
    
    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage, messages.getNumberMessage());
        messages.add(entry);
        LogChangeListener [] nowListeners = activeListeners;
        if (nowListeners == null)
        {
            synchronized (listeners)
            {
                if (activeListeners == null)
                {
                    nowListeners = listeners.toArray(new LogChangeListener [0]);
                    activeListeners = nowListeners;
                }
            }
        }
        for (LogChangeListener listener : nowListeners)
        {
            listener.onLogChanged();
        }
    }
    
    public int size()
    {
        return messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= messages.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count - 1, messages.size());
        return messages.getSegment(startFrom, indexTo);
    }

    public Iterable<LogEntry> all()
    {
        return messages.getSegment(0, messages.size());
    }
}
