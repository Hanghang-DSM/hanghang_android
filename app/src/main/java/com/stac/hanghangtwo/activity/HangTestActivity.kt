package com.stac.hanghangtwo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.stac.hanghangtwo.Entity.ImageUploadInfo

import java.util.ArrayList

class HangTestActivity : AppCompatActivity() {

    private var mDatabase: FirebaseDatabase? = null
    private var mReference: DatabaseReference? = null
    private var mChild: ChildEventListener? = null

    internal var Array: List<ImageUploadInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDatabase()
    }

    private fun initDatabase() {

        mDatabase = FirebaseDatabase.getInstance()

        mReference = mDatabase!!.getReference("log")
        mReference!!.child("log").setValue("check")

        mChild = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        mReference!!.addChildEventListener(mChild!!)
    }

    override fun onDestroy() {
        super.onDestroy()

        mReference = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database")
        mReference!!.child("All_Image_Uploads_Database").removeValue()

        for (item in Array) {

            val imageUploadInfo = ImageUploadInfo(item.imageName,
                    item.imageURL, item.imageId, item.imageSign)

            // Getting image upload ID.
            val ImageUploadId = mReference!!.child("All_Image_Uploads_Database").push().key

            // Adding image upload id s child element into databaseReference.
            mReference!!.child(ImageUploadId!!).setValue(imageUploadInfo)

        }

        mReference!!.removeEventListener(mChild!!)
    }

}
