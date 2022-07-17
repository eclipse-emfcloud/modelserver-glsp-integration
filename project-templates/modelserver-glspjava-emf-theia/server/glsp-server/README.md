# Eclipse EMF.cloud - Project Template:<br> 💾 Model Server ● 🖥️ Java GLSP-Server ● 🗂️ EMF ● 🖼️ Theia

## GLSP Server

This directory provides the initial setup of the package architecture and environment for a java-based GLSP Server.
It is based on the `TaskList` example diagram language.

For more detailed instructions and information please confer to the [README](../README.md) in the parent directory.

## Building

To build the server package execute the following in the `glsp-server` directory:

```bash
mvn clean verify
```

## Running the example

To start the `TaskList` GLSP Server process execute:

```bash
java -jar target/org.eclipse.emfcloud.integration.example.glspjavaserver-0.7.0-glsp.jar
```

Or you can use the available VS Code Task `Start TaskList GLSP Server`

## Debugging the example

To debug the GLSP Server a launch configuration is available in the `Run and Debug` view (Ctrl + Shift + D).

- `Launch TaskList GLSP Server [DEBUG]`<br>
  This config can be used to manually launch the `TaskList GLSP Server` java process.
  Breakpoints in the source files of the `glsp-server` directory will be picked up.
  In order to use this config, the Theia application backend has to be launched in `External` server mode (see `Launch TaskList Theia Backend (External GLSP Server)`).
  If the GLSP server is started via this launch config, it is possible to consume code changes immediately in the running instance via `Hot Code Replace` in the Debug toolbar.

## Using preconfigured tasks

Alternatively to executing these commands manually, you can also use the preconfigured VSCode tasks (via Menu _Terminal > Run Task..._):

- `Build TaskList GLSP Server`
- `Start TaskList GLSP Server`

## More information

For more information, please visit the [EMF.cloud Umbrella repository](https://github.com/eclipse-emfcloud/emfcloud), [EMF.cloud Website](https://www.eclipse.org/emfcloud/), [Eclipse GLSP Umbrella repository](https://github.com/eclipse-glsp/glsp) and the [Eclipse GLSP Website](https://www.eclipse.org/glsp/).
If you have questions, please raise them in the [EMF.cloud discussions page](https://github.com/eclipse-emfcloud/emfcloud/discussions) or the [Eclipse GLSP discussions page](https://github.com/eclipse-glsp/glsp/discussions) and have a look at our [communication and support options](https://www.eclipse.org/emfcloud/contact/).
