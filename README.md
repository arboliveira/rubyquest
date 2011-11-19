Ruby Quest
==========

What do you want to do?


I want to know what this is all about.
--------------------------------------

If you are here, you probably know about this already:

http://evilcorporation.com.br/rubyquest

But if not, have some answers:

http://evilcorporation.com.br/rubyquest/about.html


I want a copy of the full website to show my friends.
-----------------------------------------------------

Just run:

    static.cmd
    
The full website, in static HTML form, will be generated under: 
`output/offline/rubyquest`


I want to publish the same content, but modify the HTML for alternate visuals.
------------------------------------------------------------------------------

Page templates are here: `dynamic/src/main/webapp`

Run the dynamic website:

    dynamic.cmd
    
Wait for the server to start, open the displayed URL and navigate to the page you want.

Tweak the templates and refresh your browser to see results rendered on the fly.

When you're satisfied, just generate the static website as described above.


I want to produce sequenced versions of some other similar quests.
------------------------------------------------------------------

The original raw data used as input to render the website is here: 
`wicket/src/main/resources/rubyquest.xml`

You can repeat the download and extraction process by running:

    data-download.cmd

Study and modify the source code to:

* Point the download to the online archives where your quest is stored.
* Parse the particular format of the HTML published there.
* Extract any extra pieces of text and images that interest you.

After you've got all the data you need, you will probably want to change the 
page templates as described above. Most certainly you will need to add new pages,
remove a few, and link them around differently. Test everything using the
dynamic website, and generate the static content in the end.


I want to study and modify the source code, where do I start?
-------------------------------------------------------------

All the aforementioned scripts run a Maven "exec" command like this:

    mvn exec:exec --projects <submodule>
    
The submodule's `pom.xml` will tell you the name of the entry point main class.

Now all you need to do is fire up your favorite Java IDE and hack away!


I want to learn something useful!
---------------------------------

In fact, this project is a nice example of how you can use Apache Wicket 
to implement web pages that can be rendered both dynamically (online) and
statically (offline).

This comes handy when you're developing, say, a corporate application 
where you must, say, send an invoice to a customer through email  
(static HTML) while making the same invoice available for the customer
in the company's website (dynamic HTML). Applying the technique used 
in this project, you can handle both cases with a single class.

Have fun!
