package com.cmpt276.gameinn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cmpt276.gameinn.constant.EnumCollection.Platform;
import com.cmpt276.gameinn.models.Clip;
import com.cmpt276.gameinn.models.User;
import com.cmpt276.gameinn.repositories.Clip.IClipRepository;

@Service public class ClipService {
	@Autowired private IClipRepository clipRepository;

	public Clip addClip(Clip clip, User user) {
		String embed = getEmbed(clip.getPlatform(), clip.getSourceLink());
		Clip created = new Clip(clip.getTitle(), clip.getGameTitle(),
			clip.getTags(), clip.getPlatform(), clip.getSourceLink(), embed,
			user);
		return clipRepository.save(created);
	}

	public List<Clip> getClips(String query) {
		List<Clip> clips = clipRepository.findAll();
		List<Clip> filteredClips = search(query, clips);

		filteredClips.sort(Comparator.comparing(
			Clip::getPostedTime).reversed());
		return filteredClips;
	}

	public Clip getClipByID(Long id) {
		return clipRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("No Clip with " + id));
	}

	public Clip updateClip(Long id, Clip clip)
	throws Exception {
		Clip found = clipRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("No Clip with " + id));
		String embed = getEmbed(clip.getPlatform(), clip.getSourceLink());

		found.setTitle(clip.getTitle());
		found.setGameTitle(clip.getGameTitle());
		found.setTags(clip.getTags());
		found.setPlatform(clip.getPlatform());
		found.setSourceLink(clip.getSourceLink());
		found.setEmbed(embed);
		// found.setPostedTime(new Date().getTime());
		found.setPostedTime(found.getPostedTime().getTime());

		clipRepository.save(found);
		return found;
	}

	public void deleteClip(Long id) {
		Clip found = clipRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("No Clip with " + id));

		if (found == null)
			throw new IllegalArgumentException("No Clip with " + id);

		clipRepository.delete(found);
	}

	private List<Clip> search(String query, List<Clip> list) {
		if (query != null && !query.isEmpty()) {
			List<Clip> filteredList = new ArrayList<Clip>();

			for (Clip element : list) {
				if (element.getTitle().toLowerCase().contains(
					query.toLowerCase()) ||
					element.getGameTitle().toLowerCase().contains(
					query.toLowerCase()))
					filteredList.add(element);
			}

			return filteredList;
		}

		return list;
	}

	public static String getYTID(String ytUrl) {
		String pattern =
			"(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
		Pattern compiledPattern = Pattern.compile(pattern);
		Matcher matcher = compiledPattern.matcher(ytUrl);

		if (matcher.find())
			return matcher.group();
		else
			return "error";
	}

	public static String twitchEmbed(String link) {
		try {
			URL url = new URL(link);
			String[] path = url.getPath().split("/");

			String embed = "";

			if (path.length == 3) {
				embed = "https://player.twitch.tv/?video=" + path[2] +
					"&parent=localhost&parent=gameinn.herokuapp.com&autoplay=false";
			} else if (path.length == 4) {
				embed = "https://clips.twitch.tv/embed?clip=" + path[3] +
					"&parent=localhost&parent=gameinn.herokuapp.com";
			}

			return embed;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public static String redditEmbed(String link) {
		try {
			URL url = new URL(link);
			String[] path = url.getPath().split("/");

			String title = redditTitle(link);
			String embed = "<blockquote class='reddit-card'><a href='" + url +
				"?ref_source=embed&amp;ref=share'>" + title +
				"</a> from <a href='https://www.reddit.com/r/" + path[2] +
				"/'>" + path[2] +
				"</a></blockquote><script async src='https://embed.redditmedia.com/widgets/platform.js' charset='UTF-8'></script>";

			return embed;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public static String redditTitle(String link) {
		InputStream response = null;
		try {
			String url = "link";
			response = new URL(url).openStream();

			Scanner scanner = new Scanner(response);
			String responseBody = scanner.useDelimiter("\\A").next();
			String title = responseBody.substring(responseBody.indexOf(
				"<title>") + 7, responseBody.indexOf("</title>"));

			title = title.substring(0, title.indexOf(" : "));

			response.close();
			return title;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "error";
		}
	}

	public static String twitterEmbed(String link) {
		String embed = "";

		try {
			HttpResponse<String> jsonResponse = Unirest.get(
				"https://publish.twitter.com/oembed?url=" + link)
					.asString();
			JSONObject tweet = new JSONObject(jsonResponse.getBody());
			embed = tweet.getString("html");
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return embed;
	}

	public static String getEmbed(Platform p, String link) {
		String embed = "";

		if (p.getPlatformName() == "YouTube") {
			String ytID = getYTID(link);
			embed = "https://www.youtube.com/embed/" + ytID;
		} else if (p.getPlatformName() == "Twitch") {
			embed = twitchEmbed(link);
		} else if (p.getPlatformName() == "Reddit") {
			embed = redditEmbed(link);
		} else if (p.getPlatformName() == "Twitter") {
			embed = twitterEmbed(link);
		}

		return embed;
	}
}
