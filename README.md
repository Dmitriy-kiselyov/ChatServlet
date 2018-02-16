# ChatServlet

Chat room example using servlet technology.

Application consists of two pages. First is log-in/sign-in page:

![Imgur](https://i.imgur.com/BwLgnbm.png)

Second one is chat itself:

![Imgur](https://i.imgur.com/H1Q8fi8.png)

Client side uses __AJAX__ technology, sending the only servlet _ChatServlet_
_POST_ and _GET_ queries. After processing data server returns _JSON-string_ answer.
To choose specific _API_ method queries contain parameter `action`.
Query to server always starts with `ChatServlet?action=method_name`.

If query succeed answer will look like:

```json
{
  "response": JSONObject|JSONArray
}
```

Otherwise:

```json
{
  "error": {
    "error_code": code,
    "error_msg": "Сообщение"
  }
}
```

Server can send back such error codes:

- 1 – unknown method
- 2 – could not enter account (wrong password)
- 3 – no access rights
- 4 – required parameter missing
- 100 – unknown server error

At this point server provides such methods:

<table>
<thead>
<tr>
<th>Method</th>
<th>Query type</th>
<th>Parameters</th>
<th>Error codes</th>
<th>Answer</th>
</tr>
</thead>

<tbody>

<tr>
<td>register</td>
<td>POST</td>
<td>login, password</td>
<td>2, 4</td>
<td>
<pre>
{
  "url": "chat.jsp"
}
</pre>
</td>
</tr>

<tr>
<td>logout</td>
<td>GET</td>
<td></td>
<td></td>
<td>
Destroys session and redirects to registration page.
</td>
</tr>

<tr>
<td>sendMessage</td>
<td>POST</td>
<td>message</td>
<td>3, 4</td>
<td>
Message:
<pre>
{
  "user": "User login",
  "message": "Message",
  "time": long (in ms)
}
</pre>
</td>
</tr>

<tr>
<td>getMessages</td>
<td>GET</td>
<td></td>
<td>3, 4</td>
<td>
<pre>
JSONArray of Message
</pre>
</td>
</tr>

<tr>
<td>cometMessage</td>
<td>GET</td>
<td></td>
<td>3</td>
<td>
Uses <b>COMET</b> technology. Client waits for server answer. When answer arrives, client
should send query again. Method returns <code>Message</code> or <code>SystemMessage</code> following type:
<pre>
{
  "user": "User login",
  "status": "enter" – user entered chat
            "left" – user left chat
  "time": long (in ms)
}
</pre>

</td>
</tr>

</tbody>
</table>

At this time application has multiple defects:

- on registration fail `timeout's` overlap each other
- when new message received chat scrolls to bottom even if user browses previous messages
- __COMET__ sends one message at a time, user can miss some messages 
- `getMessages` returns all messages, it could be long and unnecessary
- __COMET__ `timeout` is infinite which is not good
- no adaptation for cellphones
- messages are stored in RAM, not database
- old browsers are not supported: Edge и IE
