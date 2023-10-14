import BaseClass from "../util/baseClass";
import axios from 'axios';
const axiosInstance = axios.create({
    baseURL: 'http://localhost:5001',
});

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class CharacterClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getCharacter', 'addNewCharacter', 'updateCharacters','getAllCharacters', 'deleteCharacterByName'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the concert for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getCharacter(character_name, errorCallback) {
        try {
            const response = await
            this.client.get(`/character/${character_name}`);
            return response.data;
        } catch (error) {
            this.handleError("getCharacter", error, errorCallback)
        }
    }

    async addNewCharacter(newCharacter, errorCallback) {
        try {
            const response = await axios.post('/character', {
                ...newCharacter
            }).then(response => response.data);

           console.log(response);
            return response;
        } catch (error) {
            this.handleError("addNewCharacter", error, errorCallback);
        }
    }

    async getAllCharacters(errorCallback) {
      try {
          const response = await this.client.get('/character');
          return response.data;
        } catch (error) {
          this.handleError("getAllCharacters", error, errorCallback);
        }
    }

    async updateCharacters(character_name, strength, dexterity, magic, mana, social, healthPoints, errorCallback) {
          try {
              const response = await this.client.post(`character`, {
                              "character_name" : character_name,
                              "strength" : strength,
                              "dexterity" : dexterity,
                              "social" : social,
                              "magic" : magic,
                              "mana" : mana,
                              "healthPoints" : healthPoints
              });

              return response.data;
            } catch (error) {
              this.handleError("updateCharacter", error, errorCallback);
            }
        }

        async deleteCharacterByName(character_name, errorCallback) {
                try {
                    const response = await this.client.delete(`character`, {
                        "character_name" : character_name
                    });
                    return null;
                } catch (error) {
                    this.handleError("deleteCharacterByName", error, errorCallback);
                }
            }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
