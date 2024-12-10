package com.mycompany.a4;
//package com.mycompany.a3;
//
//import com.codename1.ui.Button;
//import com.codename1.ui.Form;
//import com.codename1.ui.events.ActionEvent;
//import com.codename1.ui.events.ActionListener;
//
///**This form creates a looping sound and a button which pauses/plays the looping sound
//*/
//public class BGSoundForm extends Form implements ActionListener{
//
//	private BGSound bgSound;
//	private boolean bPause = false;
//	public BGSoundForm() {
//		Button bButton = new Button("Pause/Play");
//		//...[style and add bButton to the form]
//		show();
//		bButton.addActionListener(this);
//		bgSound = new BGSound("alarm.wav");
//		bgSound.play();
//}
//
//@Override
//public void actionPerformed(ActionEvent evt) {
//	
//	bPause = !bPause;
//	if (bPause)
//	bgSound.pause();
//	else
//	bgSound.play();
//	
//}
//}