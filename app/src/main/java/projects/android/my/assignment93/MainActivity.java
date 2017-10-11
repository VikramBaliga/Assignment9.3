package projects.android.my.assignment93;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] name = {"AAA","BBB","CCC"};
    String[] number = {"000","111","222"};
    String[] perm = {Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS};
    int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Permissons
    if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED )
    {
        ActivityCompat.requestPermissions(this,perm,100);
    }
        ListView listView = (ListView) findViewById(R.id.myList);
        ContactAdapter contactAdapter = new ContactAdapter(name,number);
        listView.setAdapter(contactAdapter);

        registerForContextMenu(listView);
    }

    public  class ContactAdapter extends BaseAdapter
    {
        String[] name ;
        String[] number;

        public ContactAdapter(String[] name,String[] number)
        {
            this.name=name;
            this.number=number;
        }

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.customlayout,null);

            TextView contactName = (TextView) view.findViewById(R.id.contactName);
            TextView contactNumber = (TextView) view.findViewById(R.id.contactNumber);
    pos=position;
            contactName.setText(name[position]);
            contactNumber.setText(number[position]);

            return  view;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
         super.onContextItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.smsUser:
               Intent sms = new Intent();
                sms.setAction(Intent.ACTION_SENDTO);
               // sms.
                sms.setData(Uri.parse("smsto:"+number[pos]));
                sms.putExtra("sms_body","aaaaa");
                if(sms.resolveActivity(getPackageManager())!=null) {
                    startActivity(sms);
                }
                break;
            case R.id.callUser:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number[pos]));
                startActivity(intent);
                break;

        }
        return true;
    }
}
