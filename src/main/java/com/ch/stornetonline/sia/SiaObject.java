package com.ch.stornetonline.sia;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * This class contains a Java wrapper for the Sia API. It implements
 * API functions documented in the Sia project: 
 * https://github.com/NebulousLabs/Sia/blob/master/doc/API.md
 * 
 * @author jared.michel
 */
public class SiaObject {

	private String address;
	
	/**
	 * Instantiates a new SiaObject pointing to a port 9980.
	 * 
	 */
	public SiaObject(){
		address = "http://localhost:9980";
	}
	
	/**
	 * Instantiates a new SiaObject pointing to a custom port number.
	 * 
	 * @param port_num the port number on the local host for the Sia Object to point to
	 */
	public SiaObject(int port_num){
		address = "http://localhost:" + String.valueOf(port_num);
	}
	
	/**
	 * Instantiates a new SiaObject pointing to a custom address.
	 * 
	 * @param address the string containing the custom address for the SiaObject to point to
	 */
	public SiaObject(String address){
		this.address = address;
	}
	
	//---------------------------------------------------------------------------------------------
	//The Daemon Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns a JSON array containing the Sia daemon's current constants
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Daemon.md#daemonconstants-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the current daemon's constants
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray daemonConstants() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/daemon/constants");
	}
	
	/**
	 * Shuts down the Sia daemon that is running
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Daemon.md#daemonstop-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray daemonStop() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/daemon/stop");
	}
	
	/**
	 * Returns version information of the Sia daemon that is currently running
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Daemon.md#daemonversion-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the Sia daemon's version information
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray daemonVersion() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/daemon/version");
	}
	
	//---------------------------------------------------------------------------------------------
	//Consensus Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns information about the consensus set
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Consensus.md#consensus-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing information about the Sia consensus set
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray consensus() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/consensus");
	}
	
	//---------------------------------------------------------------------------------------------
	//Gateway Module
	//---------------------------------------------------------------------------------------------
		
	/**
	 * Returns current information about the Sia gateway. This includes a list of connected peers.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Gateway.md#gateway-get-example">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing information about the Sia gateway
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray gateway() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/gateway");
	}
	
	/**
	 * Connects the Sia gateway to a specific peer. Then the peer is added to the node list if it is not already present on it.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Gateway.md#gatewayconnectnetaddress-post-example">Sia API Documentation</a>
	 * 
	 * 	@param peer_address the string that contains the IPV4 or IPV6 address of a peer the Sia gateway should connect to
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray gatewayConnect(String peer_address) throws ClientProtocolException, IOException{
		return makeRequestPOST("/gateway/connect/" + peer_address, new String[][]{{"netaddress", peer_address}});
	}
	
	/**
	 * Disconnects the Sia gateway from a specific peer. Although the peer remains in the node list.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Gateway.md#gatewaydisconnectnetaddress-post-example">Sia API Documentation</a>
	 * 
	 * 	@param peer_address the string that contains the IPV4 or IPV6 address of a peer the Sia gateway should disconnect from
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray gatewayDisconnect(String peer_address) throws ClientProtocolException, IOException{
		return makeRequestPOST("/gateway/disconnect/" + peer_address, new String[][]{{"netaddress", peer_address}});
	}
	
	//---------------------------------------------------------------------------------------------
	//Host Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns status information about the host
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#host-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing information about the Sia host
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostDetails() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/host");
	}
	
	/**
	 * Configures current hosting parameters. Only parameters included will be changed.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#host-post">Sia API Documentation</a> for parameters that can be set with this method.
	 * 
	 * 	@param parameters the 2D string array that contains hosting parameters to be changed
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostConfigure(String[][] parameters) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host", parameters);
	}
	
	/**
	 * Announces the host as a source of storage to the Sia network 
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hostannounce-post">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostAnnounce() throws ClientProtocolException, IOException{
		return makeRequestPOST("/host/announce", null);
	}
	
	/**
	 * Announces a specific address as a source of storage to the Sia network 
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hostannounce-post">Sia API Documentation</a>
	 * 
	 * 	@param address the string that contains the address to be announced to the Sia network
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostAnnounce(String address) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host", new String[][]{{"netaddress", address}});
	}
	
	/**
	 * Returns a list of folders tracked by the host's storage manager
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hoststorage-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a list of folders tracked by the host's storage manager
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostStorageInfo() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/host/storage");
	}
	
	/**
	 * Adds a specific storage folder to the host manager
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hoststoragefoldersadd-post">Sia API Documentation</a>
	 * 
	 * 	@param path the string that contains the path to the folder to be added to the host manager
	 * 	@param size the long number representing the size, in bytes, of the folder to be added to the host manager
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostAddFolder(String path, long size) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host/storage/folders/add", new String[][]{{"path", path}, {"size", String.valueOf(size)}});
	}
	
	/**
	 * Removes a specific storage folder from the host manager. All content in this folder will be moved to other folders so as to ensure
	 * no data will be lost.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hoststoragefoldersremove-post">Sia API Documentation</a>
	 * 
	 * 	@param path the string that contains the path to the folder to be removed from the host manager
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostRemoveFolder(String path) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host/storage/folders/remove", new String[][]{{"path", path}});
	}
	
	/**
	 * Removes a specific storage folder from the host manager. All content in this folder will be moved to other folders so as to ensure
	 * no data will be lost.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hoststoragefoldersremove-post">Sia API Documentation</a>
	 * 
	 * 	@param path the string that contains the path to the folder to be removed from the host manager
	 * 	@param force the boolean indicating whether the folder should be deleted even if the data inside cannot be moved to other storage folders
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostRemoveFolder(String path, boolean force) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host/storage/folders/remove", new String[][]{{"path", path}, {"force", String.valueOf(force)}});
	}
	
	/**
	 * Resizes a storage folder to a specific size in bytes.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hoststoragefoldersresize-post">Sia API Documentation</a>
	 * 
	 * 	@param path the string that contains the path to the folder
	 * 	@param size the long indicating the size, in bytes, that the folder should be resized to
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostResizeFolder(String path, long size) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host/storage/folders/remove", new String[][]{{"path", path}, {"force", String.valueOf(size)}});
	}
	
	/**
	 * Deletes a sector that is currently being hosted. This means the hosting manager will not be able to upload or provide storage proof for that
	 * sector moving forward. 
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Host.md#hoststoragesectorsdeletemerkleroot">Sia API Documentation</a>
	 * 
	 * 	@param merkle_root the string that contains the merkleroot of the sector that will be deleted
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostDeleteSector(String merkle_root) throws ClientProtocolException, IOException{
		return makeRequestPOST("/host/storage/sectors/delete/" + merkle_root, null);
	}
	
	//---------------------------------------------------------------------------------------------
	//HostDB Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns a list of all the known active hosts sorted by preference
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/HostDB.md#hostdbactive-get-example">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a list of active hosts
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostdbActiveHosts() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/hostdb/active");
	}
	
	/**
	 * Returns a list of all the known active hosts sorted by preference, limited to a certain number of hosts
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/HostDB.md#hostdbactive-get-example">Sia API Documentation</a>
	 * 
	 *  @param num the int indicating the number of hosts to return
	 *  @return the JSONArray containing a list of active hosts
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray hostdbActiveHosts(int num) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/hostdb/active?numhosts=" + String.valueOf(num));
	}
	
	//---------------------------------------------------------------------------------------------
	//Miner Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns the status of the miner
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Miner.md#miner-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing information about the miner
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray minerStatus() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/miner");
	}
	
	/**
	 * Starts a single threaded cpu miner but does nothing if the cpu miner is already running.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Miner.md#minerstart-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a response from the Sia daemon
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray minerStart() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/miner/start");
	}
	
	/**
	 * Stops the cpu miner but does nothing if the cpu miner is not running.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Miner.md#minerstop-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a response from the Sia daemon
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray minerStop() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/miner/stop");
	}
	
	//TODO: This doesn't work right now
	public byte[] minerRetrieveHeader() throws JSONException, ClientProtocolException, IOException{
		return null;
	}
	
	//TODO: Miner-Submit-Header
	
	//---------------------------------------------------------------------------------------------
	//Renter Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns the current renter settings along with metrics on the renter's spending
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renter-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the current renter settings
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterSettings() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/renter");
	}
	
	/**
	 * Modifies current renter settings
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renter-post">Sia API Documentation</a>
	 * 
	 * 	@param funds the long representing the number of hastings allocated for file contracts in a given period
	 *  @param period the long representing the duration of contracts formed
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterModifySettings(long funds, long period) throws ClientProtocolException, IOException{
		return makeRequestPOST("/renter", new String[][]{{"funds", String.valueOf(funds)}, {"period", String.valueOf(period)}});
	}
	
	/**
	 * Returns a list of active contracts
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#rentercontracts-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a list of current contracts
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterActiveContracts() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/renter/contracts");
	}
	
	/**
	 * Returns a list of files in the download queue
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renterdownloads-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a list of files in the download queue
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterDownloadQueue() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/renter/downloads");
	}
	
	/**
	 * Returns information about all stored files
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renterfiles-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a information about stored files
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterFiles() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/renter/files");
	}

	/**
	 * Deletes a renter file entry. It does not delete any downloads or original files.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renterdeletesiapath-post">Sia API Documentation</a>
	 * 
	 * 	@param sia_path the String containing the location of the file in the renter on the Sia network
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterDeleteFile(String sia_path) throws ClientProtocolException, IOException{
		return makeRequestPOST("/renter/delete/" + sia_path.replace(" ", "%20"), null);
	}
	
	/**
	 * Downloads a file to the local file system. The call will block until the file has been downloaded.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renterdownloadsiapath-get">Sia API Documentation</a>
	 * 
	 * 	@param sia_path the String containing the location of the file in the renter on the Sia network
	 * 	@param local_destination the String containing the local path where the downloaded file should be placed
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterDownloadFile(String sia_path, String local_destination) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/renter/download/" + sia_path + "?destination=" + local_destination);
	}
	
	/**
	 * Renames a file entry in the renter. An error is returned if the file path does not exist
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renterrenamesiapath-post">Sia API Documentation</a>
	 * 
	 * 	@param current_path the String containing the current sia path of the file
	 * 	@param new_path the String containing the new sia path of the file
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterRenameFile(String current_path, String new_path) throws ClientProtocolException, IOException{
		return makeRequestPOST("/renter/rename/" + current_path, new String[][]{{"newsiapath", new_path}});
	}
	
	/**
	 * Uploads a file the Sia network
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Renter.md#renteruploadsiapath-post">Sia API Documentation</a>
	 * 
	 * 	@param local_path the String containing the current local path of the file
	 * 	@param sia_path the String containing the sia path of the file
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray renterUploadFile(String local_path, String sia_path) throws ClientProtocolException, IOException{
		return makeRequestPOST("/renter/upload/" + sia_path, new String[][]{{"source", local_path}});
	}
	
	//---------------------------------------------------------------------------------------------
	//Wallet Module
	//---------------------------------------------------------------------------------------------
	
	/**
	 * Returns basic information about the wallet
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#wallet-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing basic information about the wallet
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletDetails() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet");
	}
	
	/**
	 * Loads a v0.3.3.x wallet into the current wallet
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#wallet033x-post">Sia API Documentation</a>
	 * 
	 * 	@param source the String containing the current local path of the v0.3.3.x wallet to be loaded
	 * 	@param encryption_password the String containing the encryption key of the wallet
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray wallet033x(String source, String encryption_password) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/033x", new String[][]{{"source", source}, {"encryptionpassword", encryption_password}});
	}
	
	/**
	 * Returns a new address from the wallet generated by the primary seed. An error is returned if the wallet is locked.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletaddress-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the new address
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletAddress() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/address");
	}
	
	/**
	 * Returns the list of addresses from the wallet
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletaddresses-get">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing a list of the wallet addresses
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletAddresses() throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/addresses");
	}
	
	/**
	 * Creates a backup of the wallet settings file
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletaddresses-get">Sia API Documentation</a>
	 * 
	 *  @param path the String containing the path to the location on the disk where the backup file will be saved
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletBackup(String path) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/backup?destination=" + path);
	}
	
	/**
	 * Initializes the wallet using the default english dictionary. This only needs to be done once. If it is called more than once it will return an error.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletinit-post">Sia API Documentation</a>
	 * 
	 * 	@param encryption_password the String containing the encryption password
	 *  @return the JSONArray containing a list of IDs of the transactions that were created when sending the coins
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletInit(String encryption_password) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/init", new String[][]{{"encryptionpassword", encryption_password}});
	}
	
	/**
	 * Initializes the wallet with the specified dictionary. This only needs to be done once. If it is called more than once it will return an error.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletinit-post">Sia API Documentation</a>
	 * 
	 * 	@param encryption_password the String containing the encryption password
	 *  @param dictionary the String containing the name of the dictionary that should be used when encoding the seed
	 *  @return the JSONArray containing a list of IDs of the transactions that were created when sending the coins
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletInit(String encryption_password, String dictionary) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/init", new String[][]{{"encryptionpassword", encryption_password}, {"dictionary", dictionary}});
	}
	
	/**
	 * Locks the wallet, wiping all secret keys
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletlock-post">Sia API Documentation</a>
	 * 
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletLock() throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/lock", null);
	}
	
	/**
	 * Gives the wallet a seed to track when looking for incoming transactions.
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletseed-post">Sia API Documentation</a>
	 * 
	 * 	@param encryption_password the String containing the encryption password
	 *  @param dictionary the String containing the name of the dictionary that should be used when encoding the seed
	 *  @param seed the String containing the dictionary-encoded phrase that corresponds to the seed being added to the wallet
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletSeed(String encryption_password, String dictionary, String seed) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/seed", 
				new String[][]{{"encryptionpassword", encryption_password}, {"dictionary", dictionary}, {"seed", seed}});
	}
	
	/**
	 * Returns a list of seeds in use by the wallet
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletseeds-get">Sia API Documentation</a>
	 * 
	 *  @param dictionary the String containing the name of the dictionary that should be used when encoding the seed
	 *  @return the JSONArray containing a list of seeds in use by the wallet
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletSeeds(String dictionary) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/seeds?dictionary=" + dictionary);
	}
	
	/**
	 * Send siacoins to an address
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletsiacoins-post">Sia API Documentation</a>
	 * 
	 * 	@param amount the String containing the number of hastings to be sent
	 *  @param destination the String containing the destination address
	 *  @return the JSONArray containing a list of IDs of the transactions that were created when sending the coins
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletSendSiacoins(String amount, String destination) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/siacoins", new String[][]{{"amount", amount}, {"destination", destination}});
	}
	
	/**
	 * Send siafunds to an address
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletsiafunds-post">Sia API Documentation</a>
	 * 
	 * 	@param amount the String containing the number of siafunds to be sent
	 *  @param destination the String containing the destination address
	 *  @return the JSONArray containing a list of IDs of the transactions that were created when sending the coins
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletSendSiafunds(String amount, String destination) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/siafunds", new String[][]{{"amount", amount}, {"destination", destination}});
	}
	
	/**
	 * Loads a key into the wallet that was generated by siag
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletsiagkey-post">Sia API Documentation</a>
	 * 
	 * 	@param encryption_password the String containing the key that is used to encrypt the siag key when it is imported to the wallet
	 *  @param key_path the String containing the list of filepaths, separated by commas, that point to the key files that make up the siag key
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletLoadSiagKey(String encryption_password, String key_path) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/siagkey", new String[][]{{"encryptionpassword", encryption_password}, {"keyfiles", key_path}});
	}
	
	/**
	 * Retrieves the transaction associatead with a specific transaction id
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#wallettransactionid-get">Sia API Documentation</a>
	 * 
	 *  @param transaction the String containing ID of the transaction
	 *  @return the JSONArray containing information regarding the requested transaction
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletTransactionDetails(String transaction) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/transaction/" + transaction);
	}
	
	/**
	 * Retrieves a list of transactions related to the wallet
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#wallettransactions-get">Sia API Documentation</a>
	 * 
	 *  @param start_height the long representing the height of the block where transaction history should begin
	 *  @param end_height the long representing the height of the block where transaction history should end
	 *  @return the JSONArray containing a list of transactions between the requested blocks
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletTransactions(long start_height, long end_height) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/transactions?startheight=" + String.valueOf(start_height) + "&endheight=" + String.valueOf(end_height));
	}
	
	/**
	 * Retrieves a list of transactions related to a specific address
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#wallettransactionsaddr-get">Sia API Documentation</a>
	 * 
	 *  @param address the String containing the wallet address whose transactions are being requested
	 *  @return the JSONArray containing a list of transactions related to the specified address
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletTransactions(String address) throws JSONException, ClientProtocolException, IOException{
		return makeRequestGET("/wallet/transactions/" + address);
	}
	
	/**
	 * Unlocks the wallet
	 * See <a href="https://github.com/NebulousLabs/Sia/blob/master/doc/api/Wallet.md#walletunlock-post">Sia API Documentation</a>
	 * 
	 * 	@param encryption_password the String containing the password that gets used to decrypt the file
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */
	public JSONArray walletUnlock(String encryption_password) throws ClientProtocolException, IOException{
		return makeRequestPOST("/wallet/unlock", new String[][]{{"encryptionpassword", encryption_password}});
	}
	
	//---------------------------------------------------------------------------------------------
	//Helper Variables & Methods
	//---------------------------------------------------------------------------------------------
	
	private ResponseHandler<JSONArray> response_handler = new ResponseHandler<JSONArray>(){

		public JSONArray handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			HttpEntity entity = response.getEntity();
			
			ContentType ct = ContentType.getOrDefault(entity);
			String encoding = ct.getCharset().toString();
			encoding = encoding == null ? "UTF-8" : encoding;
			if(entity != null)
				return new JSONArray("[" + EntityUtils.toString(entity, encoding) + "]");
			else
				return new JSONArray("[ {message: " + statusLine.getReasonPhrase()  + "} ]");
		}};
	
	/**
	 *	Sends and receives a GET request to and from the Sia daemon
	 * 
	 * 	@param command the String containing the formatted Sia command.
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws JSONException if an	error occurs while parsing the results returned by the Sia daemon
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */	
	protected JSONArray makeRequestGET(String command) throws JSONException, ClientProtocolException, IOException{
		//Replace spaces with %20 to complete the POST request successfully when routing file paths.
		Response r = Request.Get(address + command.replaceAll(" ", "%20"))
				.userAgent("Sia-Agent")
				.execute();
		return r.handleResponse(response_handler);
	}
	
	/**
	 *	Sends and receives a POST request to and from the Sia daemon
	 * 
	 * 	@param command the String containing the formatted Sia command.
	 * 	@param parameters the String array containing pairs of key words and values to be included with a Sia command
	 *  @return the JSONArray containing the Sia daemon's response
	 *  
	 *  @throws ClientProtocolException if an error occurs while making a request to the Sia daemon
	 *  @throws IOException if an error occurs while making a request to the Sia daemon
	 */	
	protected JSONArray makeRequestPOST(String command, String[][] parameters) throws ClientProtocolException, IOException{
		//Replace spaces with %20 to complete the POST request successfully when routing file paths.
		Response r = Request.Post(address + command.replaceAll(" ", "%20"))
				.userAgent("Sia-Agent")
				.bodyForm(createNameValuePairList(parameters))
				.execute();
		return r.handleResponse(response_handler);
	}
		
	/**
	 *	Helper method that formats a String array into a List to be used with a GET or POST command
	 * 
	 * 	@param parameters the String array containing pairs of key words and values to be included with a Sia command
	 *  @return the List<NameValuePair> containing the command's parameters
	 */	
	protected List<NameValuePair> createNameValuePairList(String[][] parameters){
		Form form = Form.form();
		if(parameters != null)
			for(int i=0; i<parameters.length; i++)
				form = form.add(parameters[i][0], parameters[i][1].replaceAll(" ", "%20"));
		List<NameValuePair> params = form.build();
		return params;
	}
	
	
}
