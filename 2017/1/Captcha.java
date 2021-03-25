public class Captcha 
{
	private final String CAPTCHA_STRING = "21752342814933766938172121674976879111362417653261522357855816893656462449168377359285244818489723869987861247912289729579296691684761143544956991583942215236568961875851755854977946147178746464675227699149925227227137557479769948569788884399379821111382536722699575759474473273939756348992714667963596189765734743169489599125771443348193383566159843593541134749392569865481578359825844394454173219857919349341442148282229689541561169341622222354651397342928678496478671339383923769856425795211323673389723181967933933832711545885653952861879231537976292517866354812943192728263269524735698423336673735158993853556148833861327959262254756647827739145283577793481526768156921138428318939361859721778556264519643435871835744859243167227889562738712953651128317624673985213525897522378259178625416722152155728615936587369515254936828668564857283226439881266871945998796488472249182538883354186573925183152663862683995449671663285775397453876262722567452435914777363522817594741946638986571793655889466419895996924122915777224499481496837343194149123735355268151941712871245863553836953349887831949788869852929147849489265325843934669999391846286319268686789372513976522282587526866148166337215961493536262851512218794139272361292811529888161198799297966893366553115353639298256788819385272471187213579185523521341651117947676785341146235441411441813242514813227821843819424619974979886871646621918865274574538951761567855845681272364646138584716333599843835167373525248547542442942583122624534494442516259616973235858469131159773167334953658673271599748942956981954699444528689628848694446818825465485122869742839711471129862632128635779658365756362863627135983617613332849756371986376967117549251566281992964573929655589313871976556784849231916513831538254812347116253949818633527185174221565279775766742262687713114114344843534958833372634182176866315441583887177759222598853735114191874277711434653854816841589229914164681364497429324463193669337827467661773833517841763711156376147664749175267212562321567728575765844893232718971471289841171642868948852136818661741238178676857381583155547755219837116125995361896562498721571413742";
	public static void main(String[] args) 
	{
		Captcha myCaptcha = new Captcha(); // Create an instance of the class
		myCaptcha.runProgram(); // Call the run program method.
	}
	
	private void runProgram() // Run program method
	{
		partTwoCaptcha(); // Call the partTwo captcha method
	}
	
	private void partOneCaptcha() // The captcha solver for part 1 of the puzzle
	{
		int captchaLength = CAPTCHA_STRING.length(); // Store the length of the string in captchaLength
		char currentChar; // Store the current character
		char nextChar; // Store the next character, helps as the string wraps around
		int total = 0; // Store the total
		for (int i = 0; i < captchaLength; i++) // For loop that will iterate over the character indexs
		{	
			currentChar = CAPTCHA_STRING.charAt(i);
			if (i == captchaLength-1) // If i is the same as the length of the captcha - 1 i.e. it's the last character
			{
				nextChar = CAPTCHA_STRING.charAt(0); // Wrap around
			}
			else 
			{
				nextChar = CAPTCHA_STRING.charAt(i+1); // Get the next character
			}
			if (currentChar == nextChar) // If the two characters are the same
			{
				total += Character.getNumericValue(currentChar); // Get the int that the character represents and add it to the total
			}
				
		}
		
		System.out.println(total); // Print the total to the console
	}
	
	private void partTwoCaptcha() // The captcha solver for part 2 of the puzzle
	{
		int captchaLength = CAPTCHA_STRING.length(); // Store the length of the string in captchaLength
		char currentChar; // Store the current character
		char nextChar; // Store the next character, helps as the string wraps around
		int nextCharIndex = 0; // The index of the next character
		int total = 0; // Store the total
		int addAmount = captchaLength/2; // Set the amount to add to each character index as half the length of the string
		for (int i = 0; i < captchaLength; i++) // For loop that will iterate over the character indexs
		{	
			currentChar = CAPTCHA_STRING.charAt(i);
			
			nextCharIndex = i + addAmount; // Add the addAmount to the current char index
			if (nextCharIndex >= captchaLength) // If the index is too high,i.e. we will get an out of bounds exception
			{
				nextCharIndex -= (captchaLength); // Subtract the captchaLength to wrap the index around
			}
			
			nextChar = CAPTCHA_STRING.charAt(nextCharIndex); // Get the character at that index
			if (currentChar == nextChar) // If the two characters are the same
			{
				total += Character.getNumericValue(currentChar); // Get the int that the character represents and add it to the total
			}
				
		}
		
		System.out.println(total); // Print the total to the console
	}
}