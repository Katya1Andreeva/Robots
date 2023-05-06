package log;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class Localization {
    public static final String RESOURCE_BUNDLE_NAME = "message";
    private AtomicReference<Locale> sk_loc = new AtomicReference<>(new Locale("en"));
    private AtomicReference<ResourceBundle> bundle =
            new AtomicReference<>(ResourceBundle.getBundle("message",
                                                           sk_loc.get()));
    private ArrayList<ArrayList<Object>> setObjectsChange =
            new ArrayList<>();

    public void addElement(AbstractButton button, String key)
    {
        ArrayList<Object> elem = new ArrayList<>();
        elem.add(button);
        elem.add(key);

        setObjectsChange.add(elem);
    }


    public void changeLocale(String language)
    {
        sk_loc.set(new Locale(language));
        bundle.set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, sk_loc.get()));
    }

    public void changeLanguage(String newLocale)
    {
        changeLocale(newLocale);
        for (ArrayList<? extends  Object> element : setObjectsChange ){
            AbstractButton button = (AbstractButton) element.get(0);
            String key = (String) element.get(1);
            setStringResource(button, key);

        }

    }

   public void setStringResource(AbstractButton element, String key) {
       element.setText(bundle.get().getString(key));

   }

    public String getStringResource(String key) {
        return bundle.get().getString(key);
    }
}
