# Eclipse EMF.cloud - Project Template:<br> 💾 Model Server ● 🖥️ Java GLSP-Server ● 🗂️ EMF ● 🖼️ Theia

## Model Server

This directory provides the initial setup of the package architecture and environment for a java-based Model Server.
It is based on the `TaskList` example diagram language.

For more detailed instructions and information please confer to the [README](../README.md) in the parent directory.

## Building

To build the server package execute the following in the `model-server` directory:

```bash
mvn clean verify
```

## Running the example

To start the `TaskList` Model Server process with the provided logging configurations execute:

```bash
java -jar target/org.eclipse.emfcloud.integration.example.modelserver-0.7.0-SNAPSHOT-standalone.jar -l=log4j2.xml
```

Or you can use the available VS Code Task `Start TaskList Model Server`

## Debugging the example

To debug the Model Server a launch configuration is available in the `Run and Debug` view (Ctrl + Shift + D).

- `Launch TaskList Model Server [DEBUG]`<br>
  This config can be used to manually launch the `TaskList Model Server` java process.
  Breakpoints in the source files of the `model-server` directory will be picked up.
  In order to use this config, the Theia application backend has to be launched after startup of the Model Server, as it pings the Model Server and if it is already running it won't startup an embedded instance.
  If the Model Server is started via this launch config, it is possible to consume code changes immediately in the running instance via `Hot Code Replace` in the Debug toolbar.

## Using preconfigured tasks

Alternatively to executing these commands manually, you can also use the preconfigured VSCode tasks (via Menu _Terminal > Run Task..._):

- `Build TaskList Model Server`
- `Start TaskList Model Server`

## More information

For more information, please visit the [EMF.cloud Umbrella repository](https://github.com/eclipse-emfcloud/emfcloud), [EMF.cloud Website](https://www.eclipse.org/emfcloud/), [Eclipse GLSP Umbrella repository](https://github.com/eclipse-glsp/glsp) and the [Eclipse GLSP Website](https://www.eclipse.org/glsp/).
If you have questions, please raise them in the [EMF.cloud discussions page](https://github.com/eclipse-emfcloud/emfcloud/discussions) or the [Eclipse GLSP discussions page](https://github.com/eclipse-glsp/glsp/discussions) and have a look at our [communication and support options](https://www.eclipse.org/emfcloud/contact/).
