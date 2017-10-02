import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PreprocessingTweets {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			List<File> filesInFolder = Files.walk(Paths.get("tweets"))
					.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
			for (int i = 0; i < filesInFolder.size(); i++) {
				FileReader fr = new FileReader(filesInFolder.get(i));
				BufferedReader br = new BufferedReader(fr);
				FileWriter fw = new FileWriter("ProcessedTweets\\tweets" + (i + 1)+".txt");
				BufferedWriter bw = new BufferedWriter(fw);
					String s = null;
					while ((s = br.readLine()) != null) {
						if (!s.contains("+")) {
							s = s.replaceAll("\"", "").replaceAll("\'", "").replaceAll("â€œ", "").replaceAll("â€�", "")
									.replaceAll("}", "").replaceAll("\\)", "").replaceAll("\\(", "")
									.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("https", "")
									.replaceAll("http", "").replaceAll("/", "").replaceAll("www", "")
									.replaceAll(" a ", " ").replaceAll(" about ", " ").replaceAll(" above ", " ")
									.replaceAll(" after ", " ").replaceAll(" again ", " ").replaceAll(" against ", " ")
									.replaceAll(" all ", " ").replaceAll(" am ", " ").replaceAll(" an ", " ")
									.replaceAll(" and ", " ").replaceAll(" any ", " ").replaceAll(" are ", " ")
									.replaceAll(" aren't ", " ").replaceAll(" as ", " ").replaceAll(" at ", " ")
									.replaceAll(" be ", " ").replaceAll(" because ", " ").replaceAll(" been ", " ")
									.replaceAll(" before ", " ").replaceAll(" being ", " ").replaceAll(" below ", " ")
									.replaceAll(" between ", " ").replaceAll(" both ", " ").replaceAll(" but ", " ")
									.replaceAll(" by ", " ").replaceAll(" cant ", " ").replaceAll(" cannot ", " ")
									.replaceAll(" could ", " ").replaceAll(" couldnt ", " ").replaceAll(" did ", " ")
									.replaceAll(" didnt ", " ").replaceAll(" do ", " ").replaceAll(" does ", " ")
									.replaceAll(" doesnt ", " ").replaceAll(" doing ", " ").replaceAll(" dont ", " ")
									.replaceAll(" down ", " ").replaceAll(" during ", " ").replaceAll(" each ", " ")
									.replaceAll(" few ", " ").replaceAll(" for ", " ").replaceAll(" from ", " ")
									.replaceAll(" further ", " ").replaceAll(" had ", " ").replaceAll(" hadn't ", " ")
									.replaceAll(" has ", " ").replaceAll(" hasnt ", " ").replaceAll(" have ", " ")
									.replaceAll(" haven't ", " ").replaceAll(" having ", " ").replaceAll(" he ", " ")
									.replaceAll(" hed ", " ").replaceAll(" hell ", " ").replaceAll(" hes ", " ")
									.replaceAll(" her ", " ").replaceAll(" here ", " ").replaceAll(" heres ", " ")
									.replaceAll(" hers ", " ").replaceAll(" herself ", " ").replaceAll(" him ", " ")
									.replaceAll(" himself ", " ").replaceAll(" his ", " ").replaceAll(" how ", " ")
									.replaceAll(" how's ", " ").replaceAll(" i ", " ").replaceAll(" id ", " ")
									.replaceAll(" ill ", " ").replaceAll(" im ", " ").replaceAll(" ive ", " ")
									.replaceAll(" if ", " ").replaceAll(" in ", " ").replaceAll(" into ", " ")
									.replaceAll(" is ", " ").replaceAll(" isnt ", " ").replaceAll(" it ", " ")
									.replaceAll(" it's ", " ").replaceAll(" its ", " ").replaceAll(" itself ", " ")
									.replaceAll(" let's ", " ").replaceAll(" me ", " ").replaceAll(" more ", " ")
									.replaceAll(" most ", " ").replaceAll(" mustn't ", " ").replaceAll(" my ", " ")
									.replaceAll(" myself ", " ").replaceAll(" no ", " ").replaceAll(" nor ", " ")
									.replaceAll(" not ", " ").replaceAll(" of ", " ").replaceAll(" off ", " ")
									.replaceAll(" on ", " ").replaceAll(" once ", " ").replaceAll(" only ", " ")
									.replaceAll(" or ", " ").replaceAll(" other ", " ").replaceAll(" ought ", " ")
									.replaceAll(" our ", " ").replaceAll(" ours ", " ").replaceAll(" ourselves ", " ")
									.replaceAll(" out ", " ").replaceAll(" over ", " ").replaceAll(" own ", " ")
									.replaceAll(" same ", " ").replaceAll(" shant ", " ").replaceAll(" she ", " ")
									.replaceAll(" shed ", " ").replaceAll(" shell ", " ").replaceAll(" shes ", " ")
									.replaceAll(" should ", " ").replaceAll(" shouldnt ", " ").replaceAll(" so ", " ")
									.replaceAll(" some ", " ").replaceAll(" such ", " ").replaceAll(" than ", " ")
									.replaceAll(" that ", " ").replaceAll(" thats ", " ").replaceAll(" the ", " ")
									.replaceAll(" their ", " ").replaceAll(" theirs ", " ").replaceAll(" them ", " ")
									.replaceAll(" themselves ", " ").replaceAll(" then ", " ")
									.replaceAll(" there ", " ").replaceAll(" theres ", " ").replaceAll(" these ", " ")
									.replaceAll(" they ", " ").replaceAll(" theyd ", " ").replaceAll(" theyll ", " ")
									.replaceAll(" they're ", " ").replaceAll(" theyve ", " ").replaceAll(" this ", " ")
									.replaceAll(" those ", " ").replaceAll(" through ", " ").replaceAll(" to ", " ")
									.replaceAll(" too ", " ").replaceAll(" under ", " ").replaceAll(" until ", " ")
									.replaceAll(" up ", " ").replaceAll(" very ", " ").replaceAll(" was ", " ")
									.replaceAll(" wasnt ", " ").replaceAll(" we ", " ").replaceAll(" wed ", " ")
									.replaceAll(" well ", " ").replaceAll(" were ", " ").replaceAll(" weve ", " ")
									.replaceAll(" were ", " ").replaceAll(" werent ", " ").replaceAll(" what ", " ")
									.replaceAll(" whats ", " ").replaceAll(" when ", " ").replaceAll(" whens ", " ")
									.replaceAll(" where ", " ").replaceAll(" where's ", " ").replaceAll(" which ", " ")
									.replaceAll(" while ", " ").replaceAll(" who ", " ").replaceAll(" whos ", " ")
									.replaceAll(" whom ", " ").replaceAll(" why ", " ").replaceAll(" whys ", " ")
									.replaceAll(" with ", " ").replaceAll(" wont ", " ").replaceAll(" would ", " ")
									.replaceAll(" wouldnt ", " ").replaceAll(" you ", " ").replaceAll(" youd ", " ")
									.replaceAll(" youll ", " ").replaceAll(" youre ", " ").replaceAll(" youve ", " ")
									.replaceAll(" your ", " ").replaceAll(" yours ", " ").replaceAll(" yourself ", " ")
									.replaceAll(" yourselves ", " ")

									.replaceAll("[!\\\"$%&()*+,;:.<=>?[\\\\]^`{|}~\\u201C\\u201D\\r\\n]", "").replaceAll(" +", " ").trim();
							
								bw.write(s);
								bw.newLine();
						}
					}
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
