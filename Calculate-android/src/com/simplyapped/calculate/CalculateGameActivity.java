package com.simplyapped.calculate;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.vending.billing.googleplay.AndroidBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.GameStateFactory.GameStateType;
import com.simplyapped.libgdx.ext.ui.AndroidOSDialog;

public class CalculateGameActivity extends AndroidApplication {

	private AndroidBillingService billing;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        GameStateFactory.setType(GameStateType.PERSISTENT);
        
        
        CalculateGame calculateGame = new CalculateGame();
        billing = new AndroidBillingService(this);
		calculateGame.setBilling(billing);
        calculateGame.setDialog(new AndroidOSDialog(this));
		CalculateGame.DEBUG = isDebuggable(this);
        
        initialize(calculateGame, cfg);
		
        Log.d(CalculateGameActivity.class.toString(), "Debug? " + isDebuggable(this));
        
        // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback() {

          // callback when session changes state
          @Override
          public void call(Session session, SessionState state, Exception exception) {
        	  if (session.isOpened())
        	  {
        	    Bitmap image = null;
        	    Request.newUploadStagingResourceWithImageRequest(session, image, null);
        		// make request to the /me API
        		  Request.newMeRequest(session, new Request.GraphUserCallback() {

        		    // callback after Graph API response with user object
        		    @Override
        		    public void onCompleted(GraphUser user, Response response) {
        		    	Toast toast = Toast.makeText(CalculateGameActivity.this, "Hello " + user.getName(), Toast.LENGTH_LONG);
        		    	toast.show();
        		    }
        		  }).executeAsync();
        	  }
          }
        });
		
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass on the activity result to the helper for handling
        if (billing.getHelper() == null || !billing.getHelper().handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        }
    }
	
	private static final X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");
	private boolean isDebuggable(Context ctx)
	{
	    boolean debuggable = false;

	    try
	    {
	        PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),PackageManager.GET_SIGNATURES);
	        Signature signatures[] = pinfo.signatures;

	        CertificateFactory cf = CertificateFactory.getInstance("X.509");

	        for ( int i = 0; i < signatures.length;i++)
	        {   
	            ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
	            X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);       
	            debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
	            if (debuggable)
	                break;
	        }
	    }
	    catch (NameNotFoundException e)
	    {
	        //debuggable variable will remain false
	    }
	    catch (CertificateException e)
	    {
	        //debuggable variable will remain false
	    }
	    return debuggable;
	}
}