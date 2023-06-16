package com.aditya.TestingApp;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    ProgressBar pb1;
    int counter = 0;

String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.editTextText);
        ed2 = findViewById(R.id.editTextText2);
        ed3 = findViewById(R.id.editTextText3);
        ed4 = findViewById(R.id.editTextText4);

        pb1 = findViewById(R.id.progressBar2);
        pb1.setVisibility(View.GONE);

        Intent intent = getIntent();
        username = intent.getStringExtra("xusername");
        password = intent.getStringExtra("xpassword");

    }
    public void sendMail(View view) {
        final String tomail = ed2.getText().toString();
        final String frommail = ed1.getText().toString();
        final String textmail = ed4.getText().toString();
        final String subjectmail = ed3.getText().toString();




//         Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


//        pb1.setVisibility();
        // Create a session with the authentication
        Session session = null;
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        SendMailTask sendMailTask = new SendMailTask(session);

        sendMailTask.execute(tomail, frommail, subjectmail, textmail);


//         create a new SendMailTask and execute it
    }

    private class SendMailTask extends AsyncTask<String, Void, Boolean> {
        private Session session;

        public SendMailTask(Session session) {
            this.session = session;
        }
        

        @Override
        protected Boolean doInBackground(String... params) {
            String tomail = params[0];
            String frommail = params[1];
            String subjectmail = params[2];
            String textmail = params[3];

            try {
                // Create a MimeMessage object
                MimeMessage message = new MimeMessage(session);

                // Set the sender and recipient addresses
                message.setFrom(new InternetAddress(frommail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tomail));

                // Set the email subject and text
                message.setSubject(subjectmail);
                message.setText(textmail);

                // Send the email
                Transport.send(message);

                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(MainActivity.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
