package com.example.carrentalranachrita.Daos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carrentalranachrita.Entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Observable;
import java.util.concurrent.Executor;

public class UserDaoTest extends TestCase {

    private DatabaseReference mockedDatabaseReference;
    @Mock
    FirebaseUser firebaseUser;

    @Before
    public void before() {
        mockedDatabaseReference = mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
    }

    @Test
    public void testInsert() {
        UserDao dao = mock(UserDao.class);
        User user  = new User();
        user.setName("John");
        user.setLastName("Brown");
        user.setEmail("jb@test.com");
        user.setPhoneNumber("7788331234");
        user.setRol("Host");
        user.setPassword("1Maj$88REt");
        when(dao.Insert(user)).thenReturn(true);
    }

    @Test
    public void testAuthen(){
        FirebaseAuth auth = mock(FirebaseAuth.class);
        String emailUser = "jb@test.com";
        String password = "1Maj$88REt";
        when(auth.signInWithEmailAndPassword(emailUser, password)).equals(firebaseUser);
    }
}