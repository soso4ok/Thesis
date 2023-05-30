package com.example.thesis.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.thesis.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UserFragment extends Fragment {

    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private ImageView avatarImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        avatarImageView = view.findViewById(R.id.avatarImageView);
        loadSavedImage();

        FrameLayout avatarView = view.findViewById(R.id.avatarView);
        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasReadStoragePermission()) {
                    openImagePicker();
                } else {
                    requestReadStoragePermission();
                }
            }
        });

        return view;
    }

    private boolean hasReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    private void loadSavedImage() {
        if (getContext() != null) {
            SharedPreferences preferences = getContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            String imageUriString = preferences.getString("avatarUri", null);
            if (imageUriString != null) {
                try {
                    Uri imageUri = Uri.parse(imageUriString);
                    InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    avatarImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception, show an error message, or set a default image
                    avatarImageView.setImageResource(R.drawable.avatar);
                }
            } else {
                // Set a default image if no saved image URI found
                avatarImageView.setImageResource(R.drawable.avatar);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    avatarImageView.setImageURI(selectedImageUri);
                    saveImageUri(selectedImageUri);
                }
            }
        }
    }

    private void saveImageUri(Uri imageUri) {
        if (getContext() != null) {
            SharedPreferences preferences = getContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("avatarUri", imageUri.toString());
            editor.apply();
        }
    }
}
