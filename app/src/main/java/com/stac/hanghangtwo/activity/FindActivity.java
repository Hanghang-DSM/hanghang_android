package com.stac.hanghangtwo.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.google.gson.Gson;
import com.stac.hanghangtwo.Entity.ImageUploadInfo;
import com.stac.hanghangtwo.R;
import com.stac.hanghangtwo.adapter.FindClothAdapter;
import com.stac.hanghangtwo.util.Id;

public class FindActivity extends AppCompatActivity {
    // For Bluetooth
    private static final int REQUEST_ENABLE_BT = 10; // 블루투스 활성화 상태
    private BluetoothAdapter bluetoothAdapter; // 블루투스 어댑터
    private Set<BluetoothDevice> devices; // 블루투스 디바이스 데이터 셋
    private BluetoothDevice bluetoothDevice; // 블루투스 디바이스
    private BluetoothSocket bluetoothSocket = null; // 블루투스 소켓
    private OutputStream outputStream = null; // 블루투스에 데이터를 출력하기 위한 출력 스트림
    int pariedDeviceCount;
    
    ImageView img_new_cloth;
    
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    
    List<ImageUploadInfo> Array = new ArrayList<>();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        if(bluetoothAdapter == null) { // 디바이스가 블루투스를 지원하지 않을 때
            Toast.makeText(FindActivity.this, "디바이스가 블루투스를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else { // 디바이스가 블루투스를 지원 할 때
            if (bluetoothAdapter.isEnabled()) { // 블루투스가 활성화 상태 (기기에 블루투스가 켜져있음)
                selectBluetoothDevice(); // 블루투스 디바이스 선택 함수 호출
            } else { // 블루투스가 비 활성화 상태 (기기에 블루투스가 꺼져있음)
                // 블루투스를 활성화 하기 위한 다이얼로그 출력
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 선택한 값이 onActivityResult 함수에서 콜백된다.
                startActivityForResult(intent, REQUEST_ENABLE_BT);
            }
        }
            final RecyclerView recyclerView = findViewById(R.id.find_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        initDatabase();

        mReference = mDatabase.getReference("All_Image_Uploads_Database"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gson gson = new Gson();
                for (DataSnapshot photoData : dataSnapshot.getChildren()) {
                    ImageUploadInfo imageName = photoData.getValue(ImageUploadInfo.class);
                    Array.add(imageName);
                }
                recyclerView.setAdapter(new FindClothAdapter(FindActivity.this,Array));
            }
    
            @Override
            public void onCancelled(DatabaseError databaseError) {
    
            }
        });
        img_new_cloth = findViewById(R.id.img_new_cloth);
        img_new_cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindActivity.this, CodyPlusActivity.class);
                startActivity(intent);
            }
        });
    
    }
    
    private void initDatabase() {
    
        mDatabase = FirebaseDatabase.getInstance();
    
        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");
    
        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
    
            }
    
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
    
            }
    
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
    
            }
    
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
    
            }
    
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
    
            }
        };
        mReference.addChildEventListener(mChild);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mReference = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database");
        mReference.child("All_Image_Uploads_Database").removeValue();

        for(ImageUploadInfo item : Array) {

            ImageUploadInfo imageUploadInfo = new ImageUploadInfo(item.imageName,
                    item.imageURL, item.imageId, item.imageSign);

            // Getting image upload ID.
            String ImageUploadId = mReference.child("All_Image_Uploads_Database").push().getKey();

            // Adding image upload id s child element into databaseReference.
            mReference.child(ImageUploadId).setValue(imageUploadInfo);

        }

        mReference.removeEventListener(mChild);
    }
    
    // 블루투스
    public void selectBluetoothDevice() {
        // 이미 페어링 되어있는 블루투스 기기를 찾습니다.
        devices = bluetoothAdapter.getBondedDevices();
        // 페어링 된 디바이스의 크기를 저장
        pariedDeviceCount = devices.size();
    
        // 페어링 되어있는 장치가 없는 경우
        if(pariedDeviceCount == 0) {
            // 페어링을 하기위한 함수 호출
        } else {  // 페어링 되어있는 장치가 있는 경우
            // 디바이스를 선택하기 위한 다이얼로그 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("페어링 되어있는 블루투스 디바이스 목록");
            // 페어링 된 각각의 디바이스의 이름과 주소를 저장
            List<String> list = new ArrayList<>();
            // 모든 디바이스의 이름을 리스트에 추가
            for(BluetoothDevice bluetoothDevice : devices) {
                list.add(bluetoothDevice.getName());
            }
            list.add("취소");
            // List를 CharSequence 배열로 변경
            final CharSequence[] charSequences = list.toArray(new CharSequence[list.size()]);
            list.toArray(new CharSequence[list.size()]);
    
            // 해당 아이템을 눌렀을 때 호출 되는 이벤트 리스너
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 해당 디바이스와 연결하는 함수 호출
                    connectDevice(charSequences[which].toString());
                }
            });
            // 뒤로가기 버튼 누를 때 창이 안닫히도록 설정
            builder.setCancelable(false);
            // 다이얼로그 생성
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    
    public void connectDevice(String deviceName) {
        // 페어링 된 디바이스들을 모두 탐색
        for(BluetoothDevice tempDevice : devices) {
            // 사용자가 선택한 이름과 같은 디바이스로 설정하고 반복문 종료
            if(deviceName.equals(tempDevice.getName())) {
                bluetoothDevice = tempDevice;
                break;
            }
        }
        // UUID 생성
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        // Rfcomm 채널을 통해 블루투스 디바이스와 통신하는 소켓 생성
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            // 데이터 송,수신 스트림을 얻어옵니다.
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void sendData(byte b) {
        try{
            // 데이터 송신
            outputStream.write(b);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
