// FILE HEADER
// Title: P103_Backend
// Authors: Annie Zhao and UW-Madison CS400
// Lecturer: Professor Florian Heimerl
// Email: azhao37@wisc.edu

import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class creates a backend using the provided placeholder tree.
 */
public class Backend implements BackendInterface{
    //Create an instance of a Tree_Placeholder
    IterableSortedCollection<Song> tree;
    //The low year end of the range that is set with getRange()
    private Integer low;
    //The high year end of the range that is set with getRange()
    private Integer high;
    //The greatest loudness set by setFilter()
    private Integer loudness;

    /**
     * This constructor creates an instance of Backend with
     * a provided tree.
     * @param tree The tree that initializes the tree of Songs.
     */
    public Backend(IterableSortedCollection<Song> tree){
        this.tree = tree;
    }

    /**
     * Loads data from the .csv file referenced by filename.  You can rely
     * on the exact headers found in the provided songs.csv, but you should
     * not rely on them always being presented in this order or on there
     * not being additional columns describing other song qualities.
     * After reading songs from the file, the songs are inserted into
     * the tree passed to the constructor.
     *
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
    @Override
    public void readData(String filename) throws IOException {
        //throw an exception if the filename can't be found
        if(filename == null || filename.isEmpty()) throw new IOException("There is trouble finding this file.");

        try {
            FileReader fileReader = new FileReader(filename);
            //Scanner to read through the file
            Scanner scnr = new Scanner(fileReader);

            String songInfo = "";
            String[] songInfoArray = new String[0];
            int character;
            String line = "";

            //keep track of headers and their indices
            HashMap<String, Integer> headersIndices = new HashMap<>();
            if (scnr.hasNextLine()) {
                String lineHeader = scnr.nextLine();
                String[] headers = lineHeader.split(",");

                //put headers/indices into HashMap
                for (int i = 0; i < headers.length; ++i) {
                    headersIndices.put(headers[i].trim().toLowerCase(), i);
                }
            }

            while (scnr.hasNextLine()) {
                line = scnr.nextLine();

                // if the line read in contains a quotation mark, use the splitLine method
                // to deal with them correctly
                if (line.contains("\"") || line.contains("'")) songInfoArray = splitLine(line);
                    //otherwise, split it directly by the comma
                else songInfoArray = line.split(",");

                //double check that the array has all the necessary items
                if (songInfoArray.length >= 9) {
                    String title = songInfoArray[headersIndices.get("title")].trim();
                    String artist = songInfoArray[headersIndices.get("artist")].trim();
                    String genres = songInfoArray[headersIndices.get("top genre")].trim();
                    int year = Integer.parseInt(songInfoArray[headersIndices.get("year")].trim());
                    int bpm = Integer.parseInt(songInfoArray[headersIndices.get("bpm")].trim());
                    int energy = Integer.parseInt(songInfoArray[headersIndices.get("nrgy")].trim());
                    int danceability = Integer.parseInt(songInfoArray[headersIndices.get("dnce")].trim());
                    int loudness = Integer.parseInt(songInfoArray[headersIndices.get("db")].trim());
                    int liveness = Integer.parseInt(songInfoArray[headersIndices.get("live")].trim());

                    //created a newSong
                    Song newSong = new Song(title, artist, genres, year, bpm, energy, danceability, loudness, liveness, new SongComparator());
                    tree.insert(newSong); // Insert into the tree
                }

            }

            //close the scanner and fileReader
            scnr.close();
            fileReader.close();
        } catch (FileNotFoundException e){
            throw new IOException("File not found");
        } catch (IOException e) {
            throw new IOException("Unable to read this file");
        }

    }

    /**
     * This private method returns the line split up correctly even when there are
     * quotation marks present.
     * @param line The String line input
     * @return String[] the array form of the split CSV line
     */
    private String[] splitLine(String line) {
        // keep track of the current part
        String currentItem = "";
        //create an ArrayList with the result
        List<String> result = new ArrayList<>();
        //create a char to keep track of the current character
        char character;

        //keep track if we're inside a pair of quotes
        boolean insideQuotes = false;

        //loop through each character in the line
        for(int i = 0; i < line.length(); ++i) {
            character = line.charAt(i);
            //if we encounter another quotation mark we increase the numQuotes
            if(character == '"') {
                insideQuotes = !insideQuotes;
            } //if there's an even # of quotes (we are not in quotes now)
            // and there's a comma, add that part of the line to the result
            else if(!insideQuotes && character == ',') {
                //currentItem = currentItem.concat(String.valueOf(character));
                result.add(currentItem);
                currentItem = "";
            } else {
                //otherwise keep adding characters to the current item
                currentItem = currentItem.concat(String.valueOf(character));
            }
        }
        result.add(currentItem);

        //return the String[] array with the splits of the csv line
        return result.toArray(new String[0]);
    }

    /**
     * Retrieves a list of song titles from the tree passed to the contructor.
     * The songs should be ordered by the songs' Year, and that fall within
     * the specified range of Year values.  This Year range will
     * also be used by future calls to the setFilter and getmost Danceable methods.
     * <p>
     * If a Loudness filter has been set using the setFilter method
     * below, then only songs that pass that filter should be included in the
     * list of titles returned by this method.
     * <p>
     * When null is passed as either the low or high argument to this method,
     * that end of the range is understood to be unbounded.  For example, a null
     * high argument means that there is no maximum Year to include in
     * the returned list.
     *
     * @param low  is the minimum Year of songs in the returned list
     * @param high is the maximum Year of songs in the returned list
     * @return List of titles for all songs from low to high, or an empty
     * list when no such songs have been loaded
     */
    @Override
    public List<String> getRange(Integer low, Integer high) {
        // re-assign the low and high ranges for the list
        this.low = low;
        this.high = high;

        //create a list of the songs that are in the specified range
        List<Song> inRangeSongs = new ArrayList<>();

        //create an instance of the iterator
        Iterator<Song> iterator = this.tree.iterator();

        //if both parts of the range are null, no need to adhere to the low/high
        //ranges, but still needs to adhere to the loudness threshold
        if(this.low == null && this.high == null){
            //iterate through the songs in the tree
            while(iterator.hasNext()){
                Song currentSong = iterator.next();
                //if there's no loudness threshold or the song's loudness is less
                //than or equal to the threshold, we add the song to the result array
                if(this.loudness == null || currentSong.getLoudness() <= this.loudness){
                    inRangeSongs.add(currentSong);
                }
            }
        } else {
            //need to adhere to loudness filter
            while(iterator.hasNext()) {
                Song currentSong = iterator.next();
                // if there's no loudness threshold or the song's loudness is less
                // than or equal to the loudness filter, need to check if the song's
                // in the range
                if (this.loudness == null || currentSong.getLoudness() < this.loudness) {
                    //if the low year is null and the year is <= high, add the song to the result
                    if (this.low == null) {
                        if (currentSong.getYear() <= this.high){
                            inRangeSongs.add(currentSong);
                            //System.out.println("adding " + currentSong.getTitle());
                        }
                    } //if the high year is null and hte year >= low, add the song to the result
                    else if (this.high == null) {
                        if (currentSong.getYear() >= this.low) {
                            inRangeSongs.add(currentSong);
                        }
                    }  // if both values aren't null and the year is in the range,
                    // add to the result array
                    else if (currentSong.getYear() >= this.low && currentSong.getYear() <= this.high) {
                        inRangeSongs.add(currentSong);
                    }
                }
            }
        }

        //songs should be ordered by songs' Year, should fall within specified range
        //inRangeSongs.sort(Comparator.comparing(Song::getYear));
        inRangeSongs.sort(new SongComparator());

        //turn into List<String> of the song titles
        List<String> inRangeSongTitles = new ArrayList<>();

        for(Song current : inRangeSongs){
            //System.out.println(current.getTitle());
            inRangeSongTitles.add(current.getTitle());

        }

        return inRangeSongTitles;
    }

    /**
     * Retrieves a list of song titles that have a Loudness that is
     * smaller than the specified threshold.  Similar to the getRange
     * method: this list of song titles should be ordered by the songs'
     * Year, and should only include songs that fall within the specified
     * range of Year values that was established by the most recent call
     * to getRange.  If getRange has not previously been called, then no low
     * or high Year bound should be used.  The filter set by this method
     * will be used by future calls to the getRange and getmost Danceable methods.
     * <p>
     * When null is passed as the threshold to this method, then no Loudness
     * threshold should be used.  This effectively clears the filter.
     *
     * @param threshold filters returned song titles to only include songs that
     *                  have a Loudness that is smaller than this threshold.
     * @return List of titles for songs that meet this filter requirement, or
     * an empty list when no such songs have been loaded
     */
    @Override
    public List<String> setFilter(Integer threshold) {

        //create an instance of the iterator
        Iterator<Song> iterator = this.tree.iterator();
        //set this instance's loudness threshold to the provided one
        this.loudness = threshold;

        //if null is passed, no Loudness threshold should be used and filter should be cleared
        // thus should just return a list of all the songs in the tree
        if(threshold == null){

            //if threshold is null, just return the list of Songs that meet the range function
            return getRange(this.low, this.high);
        }

        List<String> titles = getRange(this.low, this.high);

        List<Song> filtered = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        //iterate through the tree otherwise
        while(iterator.hasNext()){
            Song currentSong = iterator.next();

            //should only include songs that fall within specified range
            if((this.low == null || currentSong.getYear() >= this.low) && (this.high == null || currentSong.getYear() <= this.high)){
                if(currentSong.getLoudness() < threshold){
                    filtered.add(currentSong);
                }
            }

        }
        //sort by year
        //filtered.sort(Comparator.comparing(Song::getYear));
        filtered.sort(new SongComparator());

        for(Song current : filtered){
            //System.out.println(current.getTitle());
            resultList.add(current.getTitle());

        }

        return resultList;
    }

    /**
     * This method returns a list of song titles representing the five
     * most Danceable songs that both fall within any attribute range specified
     * by the most recent call to getRange, and conform to any filter set by
     * the most recent call to setFilter.  The order of the song titles in this
     * returned list is up to you.
     * <p>
     * If fewer than five such songs exist, return all of them.  And return an
     * empty list when there are no such songs.
     *
     * @return List of five most Danceable song titles
     */
    @Override
    public List<String> fiveMost() {
        //create the list of results and instance of the iterator
        List<Song> danceableSongs = new ArrayList<>();
        Iterator<Song> iterator = this.tree.iterator();

        //needs to adhere to the getRange call
        //needs to adhere to the setFilter call

        //iterate through the song titles
        //put danceable ones in sorted order as it goes along
        while(iterator.hasNext()){
            Song currentSong = iterator.next();
            //System.out.println(currentSong.getTitle());
            //needs to adhere to getRange call
            if((this.low == null || currentSong.getYear() >= this.low)
                    && (this.high == null || currentSong.getYear() <= this.high)){
                //needs to adhere to setFilter call
                if(this.loudness == null || currentSong.getLoudness() <= this.loudness) {
                    //if the song is danceable then add it to the result list
                    if(currentSong.getDanceability() > 0){
                        //System.out.println(currentSong.getTitle() + " " + currentSong.getDanceability());
                        danceableSongs.add(currentSong);
                    }
                }
            }

        }

        //order of danceability from highest danceability to lowest

        danceableSongs.sort(Comparator.comparing(Song::getDanceability).reversed());

        //need to convert to List<String> containing their titles
        List<String> danceableSongsTitles = new ArrayList<>();

        for(Song current : danceableSongs){
            danceableSongsTitles.add(current.getTitle());
        }

        //if the list's size is 0 or less than 5, return that list
        if(danceableSongsTitles.isEmpty() || danceableSongs.size() < 5) return danceableSongsTitles;
        else { //otherwise return the top 5 elements
            List<String> result = new ArrayList<>(5);
            for(int i = 0; i < 5; ++i){
                result.add(danceableSongsTitles.get(i));
            }
            return result;
        }
    }
}