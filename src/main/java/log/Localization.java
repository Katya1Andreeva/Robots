package log;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class Localization {
    public static final String RESOURCE_BUNDLE_NAME = "message";
    private AtomicReference<Locale> sk_loc = new AtomicReference<>(new Locale("en"));
    private AtomicReference<ResourceBundle> bundle =
            new AtomicReference<>(ResourceBundle.getBundle("message",
                                                           sk_loc.get()));

    private  HashMap<Class, ArrayList<AbstractButton>> handlers = new HashMap<>();
    private ArrayList<ArrayList<Object>> g =
            new ArrayList<>();

    public void addElement(AbstractButton c, String key)
    {
        ArrayList<Object> elem = new ArrayList<>();
        elem.add(c);
        elem.add(key);

        g.add(elem);
        if (handlers.containsKey(c.getClass())) {
            ArrayList<AbstractButton> a = handlers.get(c.getClass());
            a.add(c);
        } else {
            ArrayList<AbstractButton> components = new ArrayList<AbstractButton>();
            components.add(c);
            handlers.put(c.getClass(), components);
        }
    }


    public void changeLocale(String language)
    {
        sk_loc.set(new Locale(language));
        bundle.set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, sk_loc.get()));
    }

    public void changeLanguage(String newLocale)
    {
        changeLocale(newLocale);
        for (ArrayList<? extends  Object> i : g ){
            AbstractButton n = (AbstractButton) i.get(0);
            String key = (String) i.get(1);
            setStringResource(n, key);

        }

    }

   public void setStringResource(AbstractButton element, String key) {
       element.setText(new String(
               bundle.get().getString(key).getBytes(StandardCharsets.ISO_8859_1),
               StandardCharsets.UTF_8));

   }

    public String getStringResource(String key) {
        return new String(
                bundle.get().getString(key).getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
    }
}
