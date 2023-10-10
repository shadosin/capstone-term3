import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CharacterClient from "../api/characterClient";



/**
 * Logic needed for the view playlist page of the website */
class CharacterPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderCharacter'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-name-form').addEventListener('submit', this.onGet);
        document.getElementById('create-form').addEventListener('submit', this.onCreate);
        this.client = new CharacterClient();

        this.dataStore.addChangeListener(this.renderCharacter)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderCharacter() {
        let resultArea = document.getElementById("result-info");

        const character = this.dataStore.get("character");

        if (character) {
            resultArea.innerHTML = `
                <div>ID: ${character.character_name}</div>
                <div>Name: ${character.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("character", null);

        let result = await this.client.getCharacter(id, this.errorHandler);
        this.dataStore.set("character", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`);
            this.renderCharacter();
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let name = document.getElementById("character_name").value;
        let strength = document.getElementById("strength").value;
        let dexterity = document.getElementById("dexterity").value;
        let social = document.getElementById("social").value;
        let magic = document.getElementById("magic").value;
        let mana = document.getElementById("mana").value;
        let healthPoints = document.getElementById("healthPoints").value;
        this.showMessage(`clicked ${name}`);

        const newCharacter = {
            character_name: name,
            strength: strength,
            dexterity: dexterity,
            social: social,
            magic: magic,
            mana: mana,
            healthPoints: healthPoints
        }
        const addNewCharacter = await this.client.addNewCharacter(newCharacter, this.errorHandler)
         console.log(addNewCharacter)

        console.log(this.dataStore.getState())
        if (addNewCharacter) {
            this.showMessage(`Created ${this.dataStore.get("character_name")}.character_name}!`);
            await this.renderCharacter();
        } else {
            this.errorHandler("Error creating!  Try again...");
        }

    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const characterPage = new CharacterPage();
   await characterPage.mount();
    const module = characterPage
    if (module.hot) {
        module.hot.accept();
    }
};

window.addEventListener('DOMContentLoaded', main);
