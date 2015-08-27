# Scripting Interface

This API comes with some basic scripting functionality. The available commands are listed here as a reference. All commands are case insensitive and largely match the SDKAdaptor functionality.

| Command | Example | Description |
| --- | --- | --- |
| CONNECTTODRONE | CONNECTTODRONE | Connect to the drone |
| DISCONNECTFROMDRONE | DISCONNECTFROMDRONE | Disconnect from the drone |
| GETISCONNECT | GETISCONNECT | Get the status of the connection from the drone |
| STANDBYDRONE | STANDBYDRONE | Standby the drone |
| RESUMEDRONE | RESUMEDRONE | Resume the drone |
| SHUTDOWNDRONE | SHUTDOWNDRONE | Shutdown the drone |
| GETADAPTORVERSION | GETADAPTORVERSION | Get the adaptor version |
| GETSDKVERSION | GETSDKVERSION | Get the SDK version |
| GETADAPTORNAME | GETADAPTORNAME | Get the adaptor name |
| GETADAPTORMANUFACTURER | GETADAPTORMANUFACTURER | Get the adaptor manufacturer |
| GETADAPTORMODEL | GETADAPTORMODEL | Get the adaptor model |
| FLYTORELATIVE | FLYTORELATIVE _longitude_ _latitude_ _altitude_ _roll_ _pitch_ _bearing_ _[speed]_ | Fly the drone to a given relative position |
| FLYTOABSOLUTE | FLYTOABSOLUTE _longitude_ _latitude_ _altitude_ _roll_ _pitch_ _bearing_ _[speed]_ | Fly the drone to a given gps position |
| CHANGELONGITUDERELATIVE | CHANGELONGITUDERELATIVE _longitude_ _[speed]_ | Change the longitude relative|
| CHANGELATITUDERELATIVE | CHANGELATITUDERELATIVE _latitude_ _[speed]_ | Change the latitude relative|
| CHANGEALTITUDERELATIVE | CHANGEALTITUDERELATIVE _altitude_ _[speed]_ | Change the altitude relative|
| CHANGEYAWRELATIVE | CHANGEYAWRELATIVE _yaw_ _[speed]_ | Change the yaw relative|
| CHANGELONGITUDEABSOLUTE | CHANGELONGITUDEABSOLUTE _longitude_ _[speed]_ | Change the longitude absolute|
| CHANGELATITUDEABSOLUTE | CHANGELATITUDEABSOLUTE _latitude_ _[speed]_ | Change the latitude absolute|
| CHANGEALTITUDEABSOLUTE | CHANGEALTITUDEABSOLUTE _altitude_ _[speed]_ | Change the altitude absolute|
| CHANGEYAWABSOLUTE | CHANGEYAWABSOLUTE _yaw_ _[speed]_ | Change the yaw absolute|
| GOHOME | GOHOME | Flys the drone to its home position |
| GETPOSITION | GETPOSITION | prints out the drones current position |
| SETHOME | SETHOME _longitude_ _latitude_ _altitude_ _roll_ _pitch_ _bearing_ | Set the home position |
| DELAY | DELAY _time_ | Delays for _time_ milliseconds |

More commands will be added over time.