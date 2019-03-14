package com.org.locationdemo;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import static com.org.locationdemo.App.CHANNEL_1_ID;
import static com.org.locationdemo.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = NotificationManagerCompat.from(this);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);

        setContentView(R.layout.activity_main);

        requestLocationPermission();
    }

    public void sendOnchannel1(View view) {
        Notification notification= new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_1)
                .setContentTitle(editTextTitle.getText().toString())
                .setContentText(editTextMessage.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();
        notificationManager.notify(1,notification);

    }

    public void sendOnchannel2(View view) {
        Toast.makeText(this, editTextTitle.getText().toString(), Toast.LENGTH_SHORT).show();
        Notification notification= new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_1)
                .setContentTitle(editTextTitle.getText().toString())
                .setContentText(editTextMessage.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2,notification);


    }



    private void showSystemNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.cast_ic_notification_small_icon);
        mBuilder.setContentTitle("Notification Alert, Click Me");
        mBuilder.setContentTitle("Hi, This is Android Notification Details");
        Intent resultIntent = new Intent(this,ResultActivity.class );
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the Intent starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntet = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntet);
        NotificationManager mNotificationManger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // notificastionID allows tyou to update the notification latter on
        mNotificationManger.notify(1, mBuilder.build());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Intent intent = new Intent(MainActivity.this, GPSService.class);
            startService(intent);
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }
}

