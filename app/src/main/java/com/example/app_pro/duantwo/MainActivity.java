package com.example.app_pro.duantwo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_pro.duantwo.activity.DoiMatKhau;
import com.example.app_pro.duantwo.baocao.ChooserFileAdapter;
import com.example.app_pro.duantwo.baocao.NotificationHangTon;
import com.example.app_pro.duantwo.model.BackupServer;
import com.example.app_pro.duantwo.model.HangHoaNhapfm;
import com.example.app_pro.duantwo.model.HoaDonBan;
import com.example.app_pro.duantwo.model.HoaDonNhap;
import com.example.app_pro.duantwo.model.MyDatabase;
import com.example.app_pro.duantwo.model.NguoiDung;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.com.bloder.magic.view.MagicButton;
import ru.katso.livebutton.LiveButton;

import static android.R.id.message;
import static com.example.app_pro.duantwo.R.id.btnDirect;
import static com.example.app_pro.duantwo.R.id.edtNgayBaoCao;
import static com.example.app_pro.duantwo.R.id.txtThongBaoChung;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;
    LiveButton btnDangNhap, btnHuy;
    EditText edtUsername, edtPass;
    ProgressDialog title;
    int biendem = 0;
    SharedPreferences share;
    SharedPreferences.Editor editor;
    private static final String NAME_KEY = "NAME_KEY";
    private static final String PASS_KEY = "PASS_KEY";
    private String USERNAME = "";
    private String PASSWORD = "";
    String dmk = "";

    MagicButton btnBackup, btnRestore;
    DrawerLayout drawerLayout;
    LinearLayout linearLayout;

    //ProgressDialog
    ProgressDialog progressDialogBackup, progressDialogRestore;
    private static final int KET_THUC_BACKUP = 1000;
    private static final int KET_THUC_RESTORE = 1002;

    //Regex
    private static final String NGAN_CACH_COT = "#########";
    private static final String NGAN_CACH_DONG = "=========";

    //Khởi tạo database
    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    Calendar calendar;

    // Read Write file chooser

    Button buttonUp;
    TextView textFolder;

    TextView txtContent;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;

    File root;
    File curFolder;

    private List<String> fileList = new ArrayList<String>();

    //Check Final
    private static String BACKUP_RESTORE_DATA_VUONGIT = "";

    Button btnDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        initChooserFile();

        //Begin Kiểm tra kết nối mạng

        if (haveNetworkConnection() == true) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            //Backup hàng hóa--------------------------------------------------
            DatabaseReference myRefHangHoa = database.getReference("hanghoa");


            myRefHangHoa.setValue(BackupServer.ReadHangHoa());

            //Backup nhập hàng--------------------------------------------------
            DatabaseReference myRefNhapHang = database.getReference("nhaphang");

            myRefNhapHang.setValue(BackupServer.ReadNhapHang());

            //Backup xuất hàng-------------------------------------------------
            DatabaseReference myRefXuatHang = database.getReference("xuathang");

            myRefXuatHang.setValue(BackupServer.ReadXuatHang());

            //Backup người dùng-----------------------------------------------
            DatabaseReference myRefNguoiDung = database.getReference("nguoidung");

            myRefNguoiDung.setValue(BackupServer.ReadNguoiDung());

        }

        //End kiểm tra kết nối mạng
        checkAndRequestPermissions();
        share = getSharedPreferences("login", MODE_PRIVATE);

        CookeiLogin();
        if (!checkBox.isChecked()) {
            editor = share.edit();
            editor.putString(NAME_KEY, "");
            editor.putString(PASS_KEY, "");
            editor.commit();
        }

//        edtUsername.setText("admin");
//        edtPass.setText("vuongit");
        addEvent();

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;
//        NotificationMessage();

    }

    private void initChooserFile() {
//        btnDirection= (Button) findViewById(R.id.btnDirect);
//        btnDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Direction="+PathDirectory, Toast.LENGTH_SHORT).show();
//            }
//        });
//        buttonOpenDialog = (Button) findViewById(R.id.opendialog);
//        buttonOpenDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(CUSTOM_DIALOG_ID);
//            }
//        });


    }

//    private void NotificationMessage() {
//        calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
//        String ngayTonKho = formatDate.format(date);
//
//        ArrayList<HangHoaTon> arr = GetArrayListHangTon(ngayTonKho);
//
//
//        HangHoaTon Dau=arr.get(0);
//        HangHoaTon Cuoi=arr.get(arr.size()-1);
//        //Begin Notificatiom
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        Intent intent = new Intent(MainActivity.this, NotificationHangTon.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);
//
//
//        Notification.Builder notifiBuilder = new Notification.Builder(MainActivity.this);
//
//
//        String thongbao="Ngày hôm nay ("+ngayTonKho+") mặt hàng "+Cuoi.getTenHang()
//                +" có số lượng tồn kho hiều nhất là: "+Cuoi.getSoLuong()+".Mặt hàng "+
//                Dau.getTenHang()+" có số lượng tồn kho ít nhất là: "+Dau.getSoLuong();
//
//        notifiBuilder.setContentTitle("Thông báo hàng tồn");
//        notifiBuilder.setContentText(thongbao);
//        notifiBuilder.setSmallIcon(R.drawable.chuong);
//        notifiBuilder.setStyle(new Notification.BigTextStyle().bigText(thongbao));
//
//        notifiBuilder.setContentIntent(pendingIntent); //Chuyển màn hình khi click
//
//        notifiBuilder.setAutoCancel(true); //Tắt khi click vào
////Thêm 2 nút vào Notification
//
//        notifiBuilder.addAction(R.drawable.detail, "Chi tiết", pendingIntent);
//
//        notificationManager.notify(1, notifiBuilder.build()); //Hiển thị Notification
//
//
//    }

//    private ArrayList<HangHoaTon> GetArrayListHangTon(String ngayl) {
//
//        String sql = "select hanghoa.tenhanghoa,(a.TongNhap-COALESCE(b.TongXuat,0)) as tonkho\n" +
//                "from\n" +
//                "\n" +
//                "(select mahang,sum(soluong) as TongNhap\n" +
//                "from nhaphang\n" +
//                "where ngaynhap <= '" + ngayl + "'\n" +
//                "group by mahang) a\n" +
//                "\n" +
//                "left outer join\n" +
//                "\n" +
//                "(select mahang,sum(soluong) as TongXuat\n" +
//                "from xuathang\n" +
//                "where ngayxuat <= '" + ngayl + "'\n" +
//                "group by mahang) b \n" +
//                "\n" +
//                "on a.mahang=b.mahang\n" +
//                "join hanghoa on hanghoa.mahanghoa=a.mahang";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//
//
//        ArrayList<HangHoaTon> listHangTon = new ArrayList<>();
//
//        if (cursor.getCount() > 0) {
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToPosition(i);
//                String ten = cursor.getString(0);
//                int ton = cursor.getInt(1);
//                if (ton < 1) continue;
//                listHangTon.add(new HangHoaTon(ten, ton));
//
//            }
//
//            Collections.sort(listHangTon, new Comparator<HangHoaTon>() {
//                @Override
//                public int compare(HangHoaTon t1, HangHoaTon t2) {
//                    return t1.getSoLuong() - t2.getSoLuong();
//                }
//            });
//
//            return listHangTon;
//        }
//
//        return listHangTon;
//    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menudoimk:
                editor = share.edit();
                editor.putString(NAME_KEY, "admin");
                editor.putString(PASS_KEY, "");
                editor.commit();

                Intent intent = new Intent(MainActivity.this, DoiMatKhau.class);
                intent.putExtra("name", USERNAME);
                intent.putExtra("pass", PASSWORD);
                startActivity(intent);
                finish();
                break;
            case R.id.menubackup:

                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void DangNhap(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    title = ProgressDialog.
//                            show(MainActivity.this, "Dialog login", "Đang xử lý...");
//                    new CountDownTimer(2000, 1000) {
//                        @Override
//                        public void onTick(long l) {
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            title.dismiss();
//                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this, QuanLyActivity.class);
//                            startActivity(intent);
//                        }
//                    }.start();
//                } else {
//                    Toast.makeText(MainActivity.this, "Tài khoản không đúng", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("name", edtUsername.getText().toString().trim());
//                map.put("pass", edtPass.getText().toString().trim());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    // begin Context menu
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.context_menu_backup,menu);
//        menu.setHeaderTitle("Select item !");
//        //Co the set icon cho no menu.setHeaderIcon(truyen hinh vao);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.contextbackup:
//                Toast.makeText(MainActivity.this,"Ban click vao backup",Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.contextrestore:
//                Toast.makeText(MainActivity.this,"Ban click vao restore",Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }


    //End context menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quan_ly_user, menu);
        getMenuInflater().inflate(R.menu.menu_backup, menu);
//        getMenuInflater().inflate(R.menu.thu, menu);
        return true;
    }

    private void CookeiLogin() {
        edtUsername.setText(share.getString(NAME_KEY, ""));
        edtPass.setText(share.getString(PASS_KEY, ""));
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialogBackup.incrementProgressBy(1);

            switch (msg.what) {
                case KET_THUC_BACKUP:
                    Toast.makeText(MainActivity.this, "Backup thành công", Toast.LENGTH_SHORT).show();
                    dismissDialog(CUSTOM_DIALOG_ID);
                    break;
            }
        }
    };

    final Handler handlerrestore = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialogRestore.incrementProgressBy(1);
            switch (msg.what) {
                case KET_THUC_RESTORE:
                    Toast.makeText(MainActivity.this, "Restore thành công", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //Set sự kiện
    private void addEvent() {



        //Backup data

        btnBackup.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BACKUP_RESTORE_DATA_VUONGIT = "BACKUP";
                showDialog(CUSTOM_DIALOG_ID);

                btnDirection.setVisibility(View.VISIBLE);

//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Xác nhận");
//                builder.setMessage("Bạn chắc chắn muốn Backup dữ liệu?");
//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        drawerLayout.closeDrawers();
//                    }
//                });
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, final int which) {
//                        progressDialogBackup = new ProgressDialog(MainActivity.this);
//                        progressDialogBackup.setMax(100);
//                        progressDialogBackup.setCanceledOnTouchOutside(false);
//                        progressDialogBackup.setMessage("Đang tiến hành backup dữ liệu...");
//                        progressDialogBackup.setTitle("Dialog backup dữ liệu");
//                        progressDialogBackup.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                        progressDialogBackup.show();
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//
//                                    while (progressDialogBackup.getProgress() <= progressDialogBackup.getMax()) {
//                                        Thread.sleep(200);
//                                        handler.sendMessage(handler.obtainMessage());
//                                        //Begin test
//                                        Message message = new Message();
////                                        message.what=KET_THUC ;// Cần tới đâu
////                                        message.arg1=2017;//Giá trị cần truyền
////                                        handler.sendMessage(message);
//
//
//                                        //End test
//
//                                        if (progressDialogBackup.getProgress() == 100) {
//                                            progressDialogBackup.setMax(0);
//                                            progressDialogBackup.cancel();
//                                            handler.sendEmptyMessage(KET_THUC_BACKUP);
//
////                                            Toast.makeText(MainActivity.this, "Backup success", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                } catch (Exception ex) {
//                                    Toast.makeText(MainActivity.this, "Error Threading", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        }).start();
//
//
////                        Backup_HangHoa();
////                        Backup_NhapHang();
////                        Backup_XuatHang();
////                        Backup_NguoiDung();
//
//                        drawerLayout.closeDrawers();
//
//
//                    }
//                });
//                builder.show();
            }
        });

        //Restore data

        btnRestore.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BACKUP_RESTORE_DATA_VUONGIT = "RESTORE";
                showDialog(CUSTOM_DIALOG_ID);
                btnDirection.setVisibility(View.GONE);

//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Xác nhận");
//                builder.setMessage("Bạn chắc chắn muốn Restore dữ liệu?");
//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        drawerLayout.closeDrawers();
//
//                    }
//                });
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        progressDialogRestore = new ProgressDialog(MainActivity.this);
//                        progressDialogRestore.setMax(100);
//                        progressDialogRestore.setCanceledOnTouchOutside(false);
//                        progressDialogRestore.setMessage("Đang tiến hành restore dữ liệu...");
//                        progressDialogRestore.setTitle("Dialog restore dữ liệu");
//                        progressDialogRestore.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                        progressDialogRestore.show();
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//
//                                    while (progressDialogRestore.getProgress() <= progressDialogRestore.getMax()) {
//                                        Thread.sleep(200);
//                                        handlerrestore.sendMessage(handlerrestore.obtainMessage());
//                                        Message message = new Message();
//                                        if (progressDialogRestore.getProgress() == 100) {
//                                            progressDialogRestore.setMax(0);
//                                            handlerrestore.sendEmptyMessage(KET_THUC_RESTORE);
//                                            progressDialogRestore.dismiss();
//                                        }
//                                    }
//                                } catch (Exception ex) {
//                                    Toast.makeText(MainActivity.this, "Error Threading", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }).start();
//
//                        XoaNguoiDung();
//                        XoaNhapHang();
//                        XoaXuatHang();
//                        XoaHangHoa();
//                        //Restore
//
//                        RestoreHangHoa();
//                        RestoreNhapHang();
//                        RestoreXuatHang();
//                        RestoreNguoiDung();
//
//                        drawerLayout.closeDrawers();
//
//
//                    }
//                });
//                builder.show();

            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked() == true) {
                    USERNAME = edtUsername.getText().toString().trim();
                    PASSWORD = edtPass.getText().toString().trim();
                    editor = share.edit();
                    editor.putString(NAME_KEY, edtUsername.getText().toString());
                    editor.putString(PASS_KEY, edtPass.getText().toString());
                    editor.commit();
                } else {
                    editor = share.edit();
                    editor.putString(NAME_KEY, "");
                    editor.putString(PASS_KEY, "");
                    editor.commit();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DangNhap(General.URL_KIEM_TRA_TK);
                String name = edtUsername.getText().toString().trim();
                String password = edtPass.getText().toString().trim();
                if (CheckConnect(name, password)) {
                    title = ProgressDialog.
                            show(MainActivity.this, "Dialog login", "Đang xử lý...");
                    new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            title.dismiss();
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, QuanLyActivity.class);
                            startActivity(intent);
                        }
                    }.start();
                } else {
                    Toast.makeText(MainActivity.this, "Tài khoản không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


    ///------------------------------------------------------------------------------
    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialoglayout);
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                 btnDirection = dialog.findViewById(btnDirect);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });


                //Backup dữ liệu ----------------------------------------------------

                btnDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int first = PathDirectory.toString().lastIndexOf("/");
                        String result = PathDirectory.toString().substring((first + 1));

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Xác nhận");
                        builder.setMessage("Bạn chắc chắn muốn Backup dữ liệu vào thư mục "+result+"?");
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                drawerLayout.closeDrawers();
                            }
                        });
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {
                                progressDialogBackup = new ProgressDialog(MainActivity.this);
                                progressDialogBackup.setMax(100);
                                progressDialogBackup.setCanceledOnTouchOutside(false);
                                progressDialogBackup.setMessage("Đang tiến hành backup dữ liệu...");
                                progressDialogBackup.setTitle("Dialog backup dữ liệu");
                                progressDialogBackup.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDialogBackup.show();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            while (progressDialogBackup.getProgress() <= progressDialogBackup.getMax()) {
                                                Thread.sleep(200);
                                                handler.sendMessage(handler.obtainMessage());
                                                //Begin test
                                                Message message = new Message();
//                                        message.what=KET_THUC ;// Cần tới đâu
//                                        message.arg1=2017;//Giá trị cần truyền
//                                        handler.sendMessage(message);


                                                //End test

                                                if (progressDialogBackup.getProgress() == 100) {
                                                    progressDialogBackup.setMax(0);
                                                    progressDialogBackup.cancel();
                                                    handler.sendEmptyMessage(KET_THUC_BACKUP);

//                                            Toast.makeText(MainActivity.this, "Backup success", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        } catch (Exception ex) {
                                            Toast.makeText(MainActivity.this, "Error Threading", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).start();


                                Backup_HangHoa(PathDirectory);
                                Backup_NhapHang(PathDirectory);
                                Backup_XuatHang(PathDirectory);
                                Backup_NguoiDung(PathDirectory);

                                drawerLayout.closeDrawers();


                            }
                        });
                        builder.show();


//                        Toast.makeText(MainActivity.this, "Direct=" + PathDirectory, Toast.LENGTH_SHORT).show();
                    }
                });

                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));

                        //Nếu là thư mục
                        if (selected.isDirectory()) {
                            ListDir(selected);


                        } else {

                            int first = selected.toString().lastIndexOf("/");
                            String result = selected.toString().substring((first + 1));

                            if(result.equals("hanghoa.txt")&& BACKUP_RESTORE_DATA_VUONGIT.equals("RESTORE")){
                                XoaHangHoa();
                                RestoreHangHoa(selected.toString());
                                Toast.makeText(MainActivity.this, "Restore thành công bảng hàng hóa", Toast.LENGTH_SHORT).show();

                            }else if(result.equals("nhaphang.txt")&& BACKUP_RESTORE_DATA_VUONGIT.equals("RESTORE")){
                                XoaNhapHang();
                                RestoreNhapHang(selected.toString());
                                Toast.makeText(MainActivity.this, "Restore thành công bảng nhập hàng", Toast.LENGTH_SHORT).show();


                            }else if(result.equals("xuathang.txt")&& BACKUP_RESTORE_DATA_VUONGIT.equals("RESTORE")){
                                XoaXuatHang();
                                RestoreXuatHang(selected.toString());
                                Toast.makeText(MainActivity.this, "Restore thành công bảng xuất hàng", Toast.LENGTH_SHORT).show();


                            }else if(result.equals("nguoidung.txt")&& BACKUP_RESTORE_DATA_VUONGIT.equals("RESTORE")){
                                XoaNguoiDung();
                                RestoreNguoiDung(selected.toString());
                                Toast.makeText(MainActivity.this, "Restore thành công bảng người dùng", Toast.LENGTH_SHORT).show();


                            }else if( BACKUP_RESTORE_DATA_VUONGIT.equals("BACKUP")){
//                                Toa
                                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Cảnh báo");
                                builder.setMessage("Lựa chọn trái phép.Bạn phải chọn thư mục để backup dữ liệu.");
                                builder.setIcon(R.drawable.canhbao);
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

//        Dialog hopThoai=builder.create();
                                builder.show();
                            }else{
                                Toast.makeText(MainActivity.this, "Chọn không đúng file", Toast.LENGTH_SHORT).show();
                            }





//                            Toast.makeText(MainActivity.this, "File= " + result,
//                                    Toast.LENGTH_LONG).show();

                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });

                break;
        }

        return dialog;
    }

//    private void ReadFile(String path){
//        File file=new File(path);
//        try {
//            FileInputStream fis=new FileInputStream(file);
//            BufferedReader re=new BufferedReader(new InputStreamReader(fis));
//            StringBuilder builder=new StringBuilder();
//            String s="";
//            while ((s=re.readLine())!=null)
//            {
//                builder.append(s);
//            }
//            re.close();
//            txtContent.setText(builder.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    private String PathDirectory = "";

    void ListDir(File f) {
        if (f.equals(root)) {
            buttonUp.setEnabled(false);
        } else {
            buttonUp.setEnabled(true);
        }

        curFolder = f;
        textFolder.setText(f.getPath());
        PathDirectory = f.getPath();
        File[] files = f.listFiles();
        fileList.clear();

        for (File file : files) {
            fileList.add(file.getPath());
        }

//        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, fileList);


        ChooserFileAdapter adapter=new ChooserFileAdapter(MainActivity.this,fileList);
        dialog_ListView.setAdapter(adapter);
    }


    //Tiến hành restore

    private void RestoreHangHoa(String pathHangHoa) {
        ArrayList<HangHoaNhapfm> arr = GetHangHoa(pathHangHoa);
        if (arr == null) {
            return;
        }
        for (int j = 0; j < arr.size(); j++) {
            HangHoaNhapfm hh = arr.get(j);
            ContentValues contentValues = new ContentValues();
            contentValues.put("mahanghoa", hh.getMa());
            contentValues.put("tenhanghoa", hh.getTen());
            contentValues.put("ghichu", hh.getGhichu());

            sqLiteDatabase.insert("hanghoa", null, contentValues);
        }
    }

    private void RestoreNhapHang(String pathNhapHang) {
        ArrayList<HoaDonNhap> arr = GetNhapHang(pathNhapHang);
        if (arr == null) {
            return;
        }
        for (int j = 0; j < arr.size(); j++) {
            HoaDonNhap hh = arr.get(j);
            ContentValues contentValues = new ContentValues();
            contentValues.put("manhap", hh.getMaHD());
            contentValues.put("mahang", hh.getMaH());
            contentValues.put("ngaynhap", hh.getNgayHD());
            contentValues.put("soluong", hh.getSoLuongHD());
            contentValues.put("ghichu", hh.getGhichu());

            sqLiteDatabase.insert("nhaphang", null, contentValues);
        }
    }

    private void RestoreXuatHang(String pathXuatHang) {
        ArrayList<HoaDonBan> arr = GetXuatHang(pathXuatHang);
        if (arr == null) {
            return;
        }
        for (int j = 0; j < arr.size(); j++) {
            HoaDonBan hh = arr.get(j);
            ContentValues contentValues = new ContentValues();
            contentValues.put("maxuat", hh.getMahdb());
            contentValues.put("mahang", hh.getMahh());
            contentValues.put("ngayxuat", hh.getNgay());
            contentValues.put("soluong", hh.getSl());
            contentValues.put("ghichu", hh.getGhichu());

            sqLiteDatabase.insert("xuathang", null, contentValues);
        }
    }

    private void RestoreNguoiDung(String pathNguoiDung) {
        ArrayList<NguoiDung> arr = GetNguoiDung(pathNguoiDung);
        if (arr == null) {
            return;
        }
        for (int j = 0; j < arr.size(); j++) {
            NguoiDung hh = arr.get(j);
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", hh.getUsername());
            contentValues.put("password", hh.getPassword());

            sqLiteDatabase.insert("nguoidung", null, contentValues);
        }
    }

    //----------------------------Lấy dữ liệu về-------------------------------------------------
    private ArrayList<HangHoaNhapfm> GetHangHoa(String pathHangHoa) {
        ArrayList<HangHoaNhapfm> arrHangHoa = null;
        File file = null;
        try {
//            file = new File(pathHangHoa, "hanghoa.txt");
            file = new File(pathHangHoa);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader re = new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder = new StringBuilder();
            String s = "";
            while ((s = re.readLine()) != null) {
                builder.append(s);
            }
            re.close();
            arrHangHoa = Restore_HangHoa(builder.toString());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Không tìm thấy file hàng hóa", Toast.LENGTH_SHORT).show();
            progressDialogRestore.dismiss();
        } catch (IOException e) {
            Toast.makeText(this, "Lỗi đọc file hàng hóa", Toast.LENGTH_SHORT).show();
        }
        return arrHangHoa;
    }


    private ArrayList<HoaDonNhap> GetNhapHang(String pathNhapHang) {
        ArrayList<HoaDonNhap> arrHangHoa = null;
        File file = null;
        try {
            file = new File(pathNhapHang);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader re = new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder = new StringBuilder();
            String s = "";
            while ((s = re.readLine()) != null) {
                builder.append(s);
            }
            re.close();

            return arrHangHoa = Restore_NhapHang(builder.toString());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Không tìm thấy file nhập hàng", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Lỗi đọc file nhập hàng", Toast.LENGTH_SHORT).show();
        }
        return arrHangHoa;
    }


    private ArrayList<HoaDonBan> GetXuatHang(String pathXuatHang) {
        ArrayList<HoaDonBan> arrHangHoa = null;
        File file = null;
        try {
            file = new File(pathXuatHang);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader re = new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder = new StringBuilder();
            String s = "";
            while ((s = re.readLine()) != null) {
                builder.append(s);
            }
            re.close();

            return arrHangHoa = Restore_XuatHang(builder.toString());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Không tìm thấy file xuất hàng", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Lỗi đọc file xuất hàng", Toast.LENGTH_SHORT).show();
        }
        return arrHangHoa;
    }

    private ArrayList<NguoiDung> GetNguoiDung(String pathNguoiDung) {
        ArrayList<NguoiDung> arrHangHoa = null;
        File file = null;
        try {
            file = new File(pathNguoiDung);
            FileInputStream fis = new FileInputStream(file);
            BufferedReader re = new BufferedReader(new InputStreamReader(fis));
            StringBuilder builder = new StringBuilder();
            String s = "";
            while ((s = re.readLine()) != null) {
                builder.append(s);
            }
            re.close();

            return arrHangHoa = Restore_NguoiDung(builder.toString());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Không tìm thấy file người dùng", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Lỗi đọc file người dùng", Toast.LENGTH_SHORT).show();
        }
        return arrHangHoa;
    }

    //Begin Restore dữ liệu---------------------------------------------------
    private ArrayList<HangHoaNhapfm> Restore_HangHoa(String s) {

        ArrayList<HangHoaNhapfm> arr = new ArrayList<>();
        String[] arrString = s.split("\\={9}");
        for (int i = 0; i < arrString.length; i++) {

            String itemp[] = arrString[i].split("\\#{9}");

            String ma = itemp[0];
            String ten = itemp[1];
            String ghichu = itemp[2];
            HangHoaNhapfm khoa = new HangHoaNhapfm(ma, ten, ghichu);
            arr.add(khoa);
        }
        return arr;
    }

    private ArrayList<HoaDonNhap> Restore_NhapHang(String s) {

        ArrayList<HoaDonNhap> arr = new ArrayList<>();
        String[] arrString = s.split("\\={9}");
        for (int i = 0; i < arrString.length; i++) {

            String itemp[] = arrString[i].split("\\#{9}");
            String mahd = itemp[0];
            String mahh = itemp[1];
            String ngay = itemp[2];
            int sl = Integer.parseInt(itemp[3].toString());
            String ghichu = itemp[4];
            HoaDonNhap khoa = new HoaDonNhap(mahd, mahh, ngay, sl, ghichu);
            arr.add(khoa);
        }
        return arr;
    }

    private ArrayList<HoaDonBan> Restore_XuatHang(String s) {

        ArrayList<HoaDonBan> arr = new ArrayList<>();
        String[] arrString = s.split("\\={9}");
        for (int i = 0; i < arrString.length; i++) {

            String itemp[] = arrString[i].split("\\#{9}");
            String mahd = itemp[0];
            String mahh = itemp[1];
            String ngay = itemp[2];
            int sl = Integer.parseInt(itemp[3].toString());
            String ghichu = itemp[4];
            HoaDonBan khoa = new HoaDonBan(mahd, mahh, ngay, sl, ghichu);
            arr.add(khoa);
        }
        return arr;
    }

    private ArrayList<NguoiDung> Restore_NguoiDung(String s) {

        ArrayList<NguoiDung> arr = new ArrayList<>();
        String[] arrString = s.split("\\={9}");
        for (int i = 0; i < arrString.length; i++) {

            String itemp[] = arrString[i].split("\\#{9}");
            String mahd = itemp[0];
            String mahh = itemp[1];
            NguoiDung khoa = new NguoiDung(mahd, mahh);
            arr.add(khoa);
        }
        return arr;
    }

    private void XoaHangHoa() {
        sqLiteDatabase.delete("hanghoa", null, null);
    }

    private void XoaNhapHang() {
        sqLiteDatabase.delete("nhaphang", null, null);
    }

    private void XoaXuatHang() {
        sqLiteDatabase.delete("xuathang", null, null);
    }

    private void XoaNguoiDung() {
        sqLiteDatabase.delete("nguoidung", null, null);
    }


    //End Restore dữ liệu

    //Backup dữ liệu ---------------------------------------------------------->
    private void Backup_HangHoa(String pathHangHoa) {
        StringBuilder input = getHangHoa();


//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOWNLOADS)
//                , "hanghoa.txt");
        File file = new File(pathHangHoa, "hanghoa.txt");

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter outwrite = new OutputStreamWriter(fos);
            outwrite.append(input.toString());
            outwrite.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Backup_NhapHang(String pathNhapHang) {
        StringBuilder input = getNhapHang();


//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOWNLOADS)
//                , "nhaphang.txt");
        File file = new File(pathNhapHang, "nhaphang.txt");


        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter outwrite = new OutputStreamWriter(fos);
            outwrite.append(input.toString());
            outwrite.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Backup_XuatHang(String pathXuatHang) {
        StringBuilder input = getXuatHang();


//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOWNLOADS)
//                , "xuathang.txt");
        File file = new File(pathXuatHang, "xuathang.txt");


        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter outwrite = new OutputStreamWriter(fos);
            outwrite.append(input.toString());
            outwrite.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Backup_NguoiDung(String pathNguoiDung) {
        StringBuilder input = getNguoiDung();


//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DOWNLOADS)
//                , "nguoidung.txt");
        File file = new File(pathNguoiDung, "nguoidung.txt");


        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter outwrite = new OutputStreamWriter(fos);
            outwrite.append(input.toString());
            outwrite.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //End Backup dữ liệu


    private boolean CheckConnect(String ma, String pass) {
        String sql = "select username from nguoidung where username='" + ma + "' and password='" + pass + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,
                null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    private void init() {
        checkBox = (CheckBox) findViewById(R.id.check);
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPass = (EditText) findViewById(R.id.edtpass);
        btnDangNhap = (LiveButton) findViewById(R.id.btnDangNhap);
        btnHuy = (LiveButton) findViewById(R.id.btnHuy);

        btnBackup = (MagicButton) findViewById(R.id.btnBackup);
        btnRestore = (MagicButton) findViewById(R.id.btnRestore);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawlayout);
        //Init database
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }

    //Lấy dữ liệu ------------------------------------------------->

    private StringBuilder getHangHoa() {

        Cursor cursor = sqLiteDatabase.rawQuery("select * from hanghoa", null);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String ghichu = cursor.getString(2);
            stringBuilder.append(ma + NGAN_CACH_COT + ten +
                    NGAN_CACH_COT + ghichu + NGAN_CACH_DONG);
        }
        return stringBuilder;
    }

    private StringBuilder getNhapHang() {

        Cursor cursor = sqLiteDatabase.rawQuery("select * from nhaphang", null);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String mhd = cursor.getString(0);
            String mahh = cursor.getString(1);
            String ngay = cursor.getString(2);
            int sl = cursor.getInt(3);
            String ghichu = cursor.getString(4);
            stringBuilder.append(mhd + NGAN_CACH_COT + mahh + NGAN_CACH_COT + ngay
                    + NGAN_CACH_COT + sl + NGAN_CACH_COT + ghichu + NGAN_CACH_DONG);
        }
        return stringBuilder;
    }

    private StringBuilder getXuatHang() {

        Cursor cursor = sqLiteDatabase.rawQuery("select * from xuathang", null);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String mhd = cursor.getString(0);
            String mahh = cursor.getString(1);
            String ngay = cursor.getString(2);
            int sl = cursor.getInt(3);
            String ghichu = cursor.getString(4);
            stringBuilder.append(mhd + NGAN_CACH_COT + mahh + NGAN_CACH_COT + ngay
                    + NGAN_CACH_COT + sl + NGAN_CACH_COT + ghichu + NGAN_CACH_DONG);
        }
        return stringBuilder;
    }

    private StringBuilder getNguoiDung() {

        Cursor cursor = sqLiteDatabase.rawQuery("select * from nguoidung", null);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            String mk = cursor.getString(1);
            stringBuilder.append(ten + NGAN_CACH_COT + mk + NGAN_CACH_DONG);
        }
        return stringBuilder;
    }


}
