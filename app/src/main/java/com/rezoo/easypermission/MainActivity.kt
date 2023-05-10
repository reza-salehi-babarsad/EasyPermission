package com.rezoo.easypermission

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_CONTACTS
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import com.rezoo.easypermission.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity() ,EasyPermissions.PermissionCallbacks{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            if (hasPermission()){
                btnPermissin.isEnabled=false
                tvpermission.text=getString(R.string.granted_msg)
            }else{
                btnPermissin.isEnabled=true
                tvpermission.text=getString(R.string.notgranted_msg)
            }
            btnPermissin.setOnClickListener {
                requestPermission()
            }
        }

    }
    companion object{
       const val Permission_Request_Code =1
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            SettingsDialog.Builder(this).build().show()
        }else{
            requestPermission()
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        binding.apply {
            tvpermission.text=getString(R.string.granted_msg)
            btnPermissin.isEnabled = false
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    private fun hasPermission() =
        EasyPermissions.hasPermissions(
            this,
            CAMERA,
            READ_CONTACTS
        )

    private fun requestPermission(){
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.request_msg),
            Permission_Request_Code,
            CAMERA, READ_CONTACTS
        )
    }


}