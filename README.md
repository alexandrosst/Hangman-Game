# Hangman-Game
## Σύντομή Περιγραφή
<p align="justify">Το παιχνίδι αυτό δημιουργήθηκε ως μια εξαμηνιαία εργασία στο πλαίσιο του μαθήματος "Τεχνολογία Πολυμέσων" του 7ου εξαμήνου. Το project αποτελεί μια ελαφρώς παραλλαγμένη εκδοχή του γνωστού παιχνιδιού της κρεμάλας. Περιληπτικά, το παιχνίδι έχει την εξής μορφή:</p>
<ul>
	<li><p align="justify">Ο χρήστης επιλέγει το url κάποιου βιβλίου από το <a href="https://openlibrary.org/">Open Library</a>. Η εφαρμογή πραγματοποιεί ένα request στο API του Open Library, απ' όπου επιστρέφεται ως reply η περιγραφή του βιβλίου. Από την περιγραφή αυτή δημιουργείται ένα λεξικό λέξεων, το οποίο ο χρήστης μπορεί να καταστήσει ενεργό, προκειμένου ν' αντληθούν λέξεις απ' αυτό.</p></li>
	<li><p align="justify">Από το επιλεγμένο λεξικό επιλέγεται σε κάθε παιχνίδι μια λέξη τυχαία. Ο χρήστης επιλέγει όποιο άγνωστο γράμμα της λέξης επιθυμεί και πραγματοποιεί μια μαντεψιά γι' αυτό μέσα από μια λίστα από πιθανά γράμματα που του εμφανίζονται. Συνολικά έχει 6 προσπάθειες ανά λέξη πριν "καεί το ανθρωπάκι στην κρεμάλα".</p></li>
</ul>

## Περιγραφή των αρχείων
<p align="justify">Η εφαρμογή χρησιμοποιεί 5 διαφορετικούς τύπους παραθύρων, τα οποία σχεδιαστικά βρίσκονται στον κατάλογο <a href="https://github.com/alexandrosst/Hangman-Game/tree/main/resources">resources</a> ως <code>.fxml</code> αρχεία. Επιπλέον, σε κάθε παράθυρο προσδίδεται κάποια λειτουργικότητα με τη βοήθεια ενός αντιστοιχισμένου controller. Έχουμε λοιπόν τα ακόλουθα παράθυρα:</p>
<ul>
	<li><p align="justify"><a href="https://github.com/alexandrosst/Hangman-Game/tree/main/resources/MainWindow">MainWindow</a> ⇝ Πρόκειται για το κύριο παράθυρο της εφαρμογής. Το παράθυρο στην αρχή είναι ανενεργό, καθότι χρειάζεται να προηγηθεί η επιλογή ενός λεξικού. Υπάρχει ο αντίστοιχος controller <a href="https://github.com/alexandrosst/Hangman-Game/blob/main/MainWindowController.java">MainWindowController.java</a>.</p></li>
	<li><p align="justify"><a href="https://github.com/alexandrosst/Hangman-Game/tree/main/resources/CreateNewDictionary">CreateNewDictionary</a> ⇝ Πρόκειται για το παράθυρο στο οποίο ο χρήστης δημιουργεί ένα νέο λεξικό δίνοντας ένα όνομα <code>Dictionary ID</code>. Τα λεξικά αποθηκεύονται στον κατάλογο <a href="https://github.com/alexandrosst/Hangman-Game/tree/main/medialab">medialab</a> με τη μορφή <code>hangman_{Dictionary ID}.txt</code>. Ο κατάλογος αυτός περιέχει κάποια έτοιμα λεξικά. Υπάρχει ο αντίστοιχος controller <a href="https://github.com/alexandrosst/Hangman-Game/blob/main/CreateNewDictionaryController.java">CreateNewDictionaryController.java</a>.</p></li>
	<li><p align="justify"><a href="https://github.com/alexandrosst/Hangman-Game/tree/main/resources/SearchDictionary">SearchDictionary</a> ⇝ Πρόκειται για το παράθυρο στο οποίο ο χρήστης επιλέγει κάποιο λεξικό ως ενεργό λεξικό χρησιμοποιώντας μόνο το <code>Dictionary ID</code> που όρισε όταν δημιούργησε το λεξικό, δηλαδή χωρίς το πρόθεμα "hangman_" με το οποίο αποθηκεύετηκε στον υποφάκελο medialab. Υπάρχει ο αντίστοιχος controller <a href="https://github.com/alexandrosst/Hangman-Game/blob/main/SearchDictionaryController.java">SearchDictionaryController.java</a>.</p></li>
	<li><p align="justify"><a href="https://github.com/alexandrosst/Hangman-Game/tree/main/resources/DictionaryInfo">DictionaryInfo</a> ⇝  Πρόκειται για το παράθυρο στο οποίο παρουσιάζει όλα τα στατιστικά στοιχεία για το τρέχον λεξικό. Υπάρχει ο αντίστοιχος controller <a href="https://github.com/alexandrosst/Hangman-Game/blob/main/DictionaryInfoController.java">DictionaryInfoController.java</a>.</p></li>
	<li><p align="justify"><a href="https://github.com/alexandrosst/Hangman-Game/tree/main/resources/DisplayRounds">DisplayRounds</a> ⇝  Πρόκειται για το παράθυρο στο οποίο εμφανίζονται πληροφορίες για τα 5 τελευταία παιχνίδια. Κάθε παιχνίδι αποτελεί ένα αντικείμενο η έννοια του οποίου προσδιορίζεται στο αρχείο <a href="https://github.com/alexandrosst/Hangman-Game/blob/main/Rounds.java">Rounds.java</a>. Υπάρχει ο αντίστοιχος controller <a href="https://github.com/alexandrosst/Hangman-Game/blob/main/DisplayRoundsController.java">DisplayRoundsController.java</a>.</p></li>
</ul> 

## Παραδοχές
Αξιοσημείωτες είναι οι ακόλουθες σχεδιαστικές παραδοχές που θεωρήθηκαν. Έχουμε:
<ul>
	<li><p align="justify">Αν υπάρχουν λέξεις που περιλαμβάνουν ενωτικά/κάτω παύλες μεταξύ τους (π.χ. ex-girlfriend) ή ειδικούς χαρακτήρες/αριθμούς στο εσωτερικό ως λάθη εκ παραδρομής (π.χ. happ%ine6ss), τότε απορρίπτονται. Αν οι ειδικοί χαρακτήρες/αριθμοί βρίσκονται στην αρχή ή το τέλος, τότε η λέξη χωρίς τον ειδικό χαρακτήρα/αριθμό μπορεί να γίνει δεκτή.</p></li>
	<li><p align="justify">Παρατηρήθηκε ότι υπάρχουν βιβλία στα οποία η σύνταξη JSON διαφέρει. Η εκφώνηση αναφέρει την περίπτωση που το πεδίο description έχει τη μορφή <code>"description": {"type": "/type/text", "value": "..."}</code> (π.χ. το βιβλίο The Lord of the Rings του Τolkien), οπότε και μας  ενδιαφέρει η τιμή του πεδίου value. Η εφαρμογή ωστόσο σχεδιάστηκε και για να υλοποιεί μια σύνταξη JSON της μορφής <code>"description": "..."</code> (π.χ. το βιβλίο IT του Stephen King).</p></li>
	<li><p align="justify">Κάθε φορά που ο παίκτης θέλει να παίξει, πρέπει να πατήσει το αντίστοιχο γράμμα πρώτα και μετά να γράψει το έγκυρο γράμμα που επιθυμεί. Αν πρόκειται για σωστή επιλογή, τότε συμπληρώνεται αυτόματα στη λέξη και δεν μπορεί πλέον να το τροποποιήσει.</p></li>
	<li><p align="justify">Υπάρχει ένας μέγιστος αριθμός γραμμάτων που μπορεί να εμφανιστεί στο χώρο του παραθύρου. Έχει ληφθεί μέριμνα να είναι αρκετά μεγάλος, ώστε να καλύπτει - και με το παραπάνω - τις ανάγκες της μεγαλύτερης αγγλικής λέξης.</p></li>
	<li><p align="justify">Όταν τελειώσει ένα παιχνίδι, τότε η λέξη του παιχνιδιού αυτού δεν διαγράφεται από το ενεργό λεξικό, αλλά συνεχίζει να υπάρχει. Δηλαδή, μπορεί να ξαναεμφανιστεί σε κάποιο νέο παιχνίδι με το ίδιο λεξικό.</p></li>
	<li><p align="justify">Σε κάθε παράθυρο υπάρχει ένα πεδίο που χρησιμοποιείται για τη διοχέτευση μηνυμάτων από την εφαρμογή προς τον χρήστη, όπως ένα μήνυμα λάθους, ένα μήνυμα νίκης κτλ. δίχως την εμφάνιση κάποιου popup παράθυρου. Εμφανίζεται επίσης με το κατάλληλο χρώμα (π.χ. κόκκινο χρώμα για σφάλμα).</p></li>
	<li><p align="justify">Αν δημιουργηθεί νέο λεξικό με όνομα ενός άλλου ήδη υπάρχοντος στον φάκελο medialab, τότε γίνεται αντικατάσταση του παλιού από το νέο λεξικό.</p></li>
	<li><p align="justify">Ο υποφάκελος medialab περιλαμβάνει εκ προοιμίου κάποια έτοιμα λεξικά για ευκολία. Η εφαρμογή δουλεύει κανονικά και χωρίς αυτός να υπάρχει εξαρχής.</p></li>
</ul>

## Απαιτήσεις
<ul>
	<li>windows 10/11</li>
	<li>java 17.0.1</li>
	<li>Gson 2.8.9</li>
</ul>

## Εκτέλεση εφαρμογής
Για τη μεταγλώττιση χρησιμοποιούμε την εντολή:
```python
javac -d . -cp ".;libraries/Gson/gson-2.8.9.jar" --module-path "./libraries/JavaFX/lib" --add-modules javafx.controls,javafx.fxml *.java
```

Για την εκτέλεση χρησιμοποιούμε την εντολή:
```python
java -cp ".;libraries/Gson/gson-2.8.9.jar" --module-path "./libraries/JavaFX/lib" --add-modules javafx.controls,javafx.fxml Main
```