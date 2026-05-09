# cerberus

A simple application that generates random passwords with customizable patterns. The generation is based on Java's SecureRandom and Instant timestamp at time of usage to increase entropy.

### options

The password will be generated based on the following customization options and any unrecognized argument passed to the application will be ignored.

<details>
<summary>length</summary>

The `length` will determine the exact size of the password to be generated.

> `default` 20<br />
> `values` anywhere between 16 through 32

```bash
cerberus --length=20
cerberus --l=20
```

</details>

<details>
<summary>alphanumeric</summary>

The `alphanumeric` flag will determine if the password consists only of alphanumeric characters or not.

> `default` false<br />
> `values` true if present otherwise false

```bash
cerberus --alphanumeric
cerberus --a
```

</details>

### docker

To run the application in Docker, you simply build the image.

```sh
docker build . -t cerberus
```

Then run the container with the desired generation option arguments.

```sh
docker run --rm cerberus --length=25 --alphanumeric
```
