![image](https://user-images.githubusercontent.com/33568801/112197743-13d72200-8c15-11eb-9c23-f1d85fff7713.png)

![license](https://img.shields.io/github/license/Cryston98/extension_counter)
![issues](https://img.shields.io/github/issues/Cryston98/extension_counter)
![build](https://img.shields.io/appveyor/build/Cryston98/extension_counter)

This plugin categorizes software files by extension, to illustrate the contents of a software project

The files generated can be integrated in Dx Platform

## Installation

* Start by downloading a release version from this repository. Click on [Releases](https://github.com/Cryston98/extension_counter/releases "Go to Releases") and download the .rar file
* Extract the contents of the .rar file in the desired location
 ### [Optional] Running this program as a Docker image
  * For this step, you must have Docker installed. You can download it from [here](https://www.docker.com/)
  * After Docker installation, open the command prompt and run the following command:
    ```bash
      docker run cryston/extension-map:1.0
    ```
    
  * Create an empty **.json** file with the project name from config(e.g. **obs-studio-output.json**)
  * Modify the projectPath from config file by adding **/sources**
  * To run the command, we have to create 3 volumes: one for the output file, one for the config.properties file located
  in Docker and the local config file, and one for the sources folder and the path to our project.

## Usage

* After all the files have been extracted, the folder should look like so:

![image](https://user-images.githubusercontent.com/33568801/112189544-c8207a80-8c0c-11eb-9275-b01644009864.png)



* Modify the project path, project name and os_type entries from *config.properties* file. For example:
```bash
  projectName=obs-studio
  projectPath=C:\\Users\\Roland\\CES_obs
  os_type=windows
```

* **IMPORTANT** 

   * **DO NOT** add spaces before or after the config file entries
   * Specify the **absolute** project path(starting from the root folder)
   * os_type can be **windows** or **linux** (in lowercase)

* Open a command prompt(or Powershell window) and type in either of the following commands:
```bash
  run.bat
  run.sh
```

* or double click either one of the files..
* If everything was configured correctly, a file named **"projectName-output.json"** file should be generated, and the command prompt window should like this:

![image](https://user-images.githubusercontent.com/33568801/112191042-43cef700-8c0e-11eb-916a-c1c737beabe5.png)


![image](https://user-images.githubusercontent.com/33568801/112190671-e8046e00-8c0d-11eb-908f-ff415c2f8d01.png)


## The team

 * Deaconu Adrian-Sebastian
 * Tamas Roland

## Contributions

 If you would like to contribute to this project, you can fork the repository and modify the files,
 or you can clone the repository.
 
## Credits
 We would like to thank Java for making the development of this plugin possible.
