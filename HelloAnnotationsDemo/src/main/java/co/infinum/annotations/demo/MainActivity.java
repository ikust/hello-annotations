package co.infinum.annotations.demo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import co.infinum.annotations.Hello;

@Hello
public class MainActivity extends ActionBarActivity {


    @Hello
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        showAnnotationMessage();
    }

    private void showAnnotationMessage() {
        Object helloAnnotations = null;
        try {
            helloAnnotations = Class.forName("co.infinum.annotations.generated.HelloAnnotations").newInstance();
            String message = (String)invoke(helloAnnotations, "getMessage");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage(message);
            builder.setPositiveButton("Ok", null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error processing annotations");
            builder.setMessage(e.getMessage());
            builder.setPositiveButton("Ok", null);
            builder.show();
        }
    }

    public static Object invoke(Object object, String methodName, Object... args) throws Exception {
        Class[] parameterTypes = new Class[args.length];
        Exception runtimeException = new Exception("Problem with dynamic invocation of method '" + methodName + "'");
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        try {
            Method method = object.getClass().getMethod(methodName, parameterTypes);
            return method.invoke(object, args);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (NoSuchMethodException e) {

            e.printStackTrace();
            throw runtimeException;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw runtimeException;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
