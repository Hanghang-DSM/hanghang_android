/*package com.stac.hanghangtwo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

import com.stac.hanghangtwo.Entity.ImageUploadInfo
import com.stac.hanghangtwo.adapter.FindClothAdapter

import java.util.ArrayList

class HangTestActivity : AppCompatActivity() {

    val mDatabase: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
    lateinit var mReference: DatabaseReference
    val mChild: ChildEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {}
        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
        override fun onCancelled(databaseError: DatabaseError) {}
    }

    val array: ArrayList<ImageUploadInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDatabase()
    }

    private fun initDatabase() {
        mReference = mDatabase.getReference("log")
        mReference.child("log").setValue("check")

        mReference.addChildEventListener(mChild)

        mReference = mDatabase.getReference("All_Image_Uploads_Database") // 변경값을 확인할 child 이름
        mReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (photoData in dataSnapshot.children) {
                    val imageName = photoData.getValue(ImageUploadInfo::class.java) ?: ImageUploadInfo("Error","",-1,true)
                    array.add(imageName)
                }
                recyclerView.setAdapter(FindClothAdapter(array))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        mReference = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database")
        mReference.setValue(null)

        for (item in Array) {

            val imageUploadInfo = ImageUploadInfo(item.imageName,
                    item.imageURL, item.imageId, item.imageSign)

            // Getting image upload ID.
            val ImageUploadId = mReference.child("All_Image_Uploads_Database").push().key

            // Adding image upload id s child element into databaseReference.
            mReference.child(ImageUploadId!!).setValue(imageUploadInfo)

        }

        mReference.removeEventListener(mChild)
    }

}
*/