(ns enthalpy.words)

;; ---------------------------------------------------------------------------
;; Level letter definitions — each entry is the SET OF NEW letters added at
;; that level. Accumulated sets are computed at runtime by level-letters.
;; Order follows Enthium v14 home-row frequency (highest-freq first).
;; ---------------------------------------------------------------------------

(def level-new-letters
  {1 #{"h" "t" "n" "s" "e" "a" "i" "r"}
   2 #{"o" "c" "l" "d"}
   3 #{"u" "y" "m" "p"}
   4 #{"b" "w" "f" "g"}
   5 #{"k" "v" "j" "x"}
   6 #{"'" "," "." ";"}
   7 nil   ; all letters — no restriction
   8 nil}) ; sentences mode

(defn level-letters
  "Return the cumulative set of available single-character strings for level n.
   Returns nil for levels 7+ (= no restriction, all words allowed)."
  [n]
  (if (>= n 7)
    nil
    (reduce into #{} (map level-new-letters (range 1 (inc n))))))

;; ---------------------------------------------------------------------------
;; Master word list — common English words ordered loosely by frequency.
;; Lower-case only so that the default (allow-caps? false) setting works.
;; Words were chosen to guarantee a useful training set at every level.
;; ---------------------------------------------------------------------------

(def master-list
  ;; ---- level-1-friendly (h t n s e a i r) --------------------------------
  ["it" "is" "in" "at" "as" "an" "he" "her" "his" "has"
   "she" "sir" "sit" "set" "net" "ten" "hen" "ran" "rat"
   "tar" "tan" "tin" "sin" "sea" "tea" "era" "ear" "eat"
   "air" "art" "ask" "ire" "ash" "err" "ire" "inn"
   "the" "and" "that" "this" "than" "then" "here" "there"
   "their" "these" "his" "her" "hers" "its"
   "star" "stir" "site" "sire" "hire" "tire" "rate" "rain"
   "rant" "rein" "rein" "rise" "rite" "rash" "rest" "rein"
   "heat" "hear" "hate" "hair" "hare" "hint" "hits" "hiss"
   "nest" "near" "neat" "nine"
   "test" "thin" "shin" "tier" "ties" "tins" "tear" "tens"
   "earn" "ears" "east" "eats"
   "sane" "sent" "seen" "seat" "shin" "shin" "siren"
   "train" "stain" "stern" "shire" "snare" "shirt"
   "heart" "earth" "three" "their" "shins" "tires"
   "sister" "artist" "insert" "entire" "strain" "stairs"
   "shrine" "inset" "enters" "trends" "trains" "hearts"
   "interest" "entries" "eastern" "senator" "terrain"
   "resistor" "restraint" "rainiest" "inherits" "anteater"

   ;; ---- level-2 adds (o c l d) -------------------------------------------
   "do" "so" "to" "on" "no" "or" "old" "one" "all"
   "oil" "ore" "ode" "ode" "cod" "cot" "cog" "col" "con"
   "doc" "doe" "dot" "dote" "dose" "dote"
   "lot" "lost" "lock" "load" "loan" "lace" "lore" "loin"
   "lion" "list" "lend" "lash" "lace" "laid" "land" "lane"
   "also" "cold" "cord" "core" "code" "coin" "cost" "coal"
   "coat" "colt" "cola" "coil" "cola"
   "does" "done" "dose" "door" "dore" "dole" "dolt"
   "hold" "hole" "horn" "host" "hose" "honor" "hotel"
   "node" "nose" "note" "noel" "notes" "noels"
   "road" "role" "root" "rods" "rode" "rose" "rote"
   "said" "sell" "sole" "sold" "soil" "soil" "slots" "slid"
   "told" "toad" "toil" "tone" "tool" "told" "toll"
   "idea" "idol" "idle"
   "shade" "shore" "those" "stone" "snore" "slide" "social"
   "send" "olds" "cold" "docs" "dots" "cons" "lots" "oils"
   "called" "coiled" "dental" "details" "discord"
   "colonel" "consider" "corridor" "scholar" "loincloth"
   "cold" "door" "coral" "radio" "train" "solid" "stride"
   "hold" "road" "hired" "stone" "dance" "candle" "castle"
   "clean" "close" "cloth" "cloud" "clad" "cleat" "coals"
   "island" "action" "ancient" "soldier" "consider"
   "distance" "addition" "tradition" "standard" "noticed"
   "national" "attention" "condition" "decision" "second"
   "historic" "calendar" "cardinal" "clarinet" "doctrine"

   ;; ---- level-3 adds (u y m p) -------------------------------------------
   "up" "my" "may" "say" "day" "pay" "way" "lay" "hay"
   "you" "use" "yes" "yet" "yam" "yap" "yew" "yore"
   "map" "mat" "mad" "man" "mar" "mast" "mane" "mare" "male"
   "mile" "mint" "mild" "mist" "miss" "mind" "mine" "mice"
   "much" "must" "muse" "mute" "mule" "mask" "made" "main"
   "palm" "pale" "paid" "pain" "past" "path" "part" "park"
   "play" "plan" "plus" "plot" "plum" "plop" "plod" "plug"
   "ploy" "plume" "plans" "plant" "plain" "place" "plane"
   "put" "pun" "pun" "pub" "pit" "pin" "pig" "pie" "pod"
   "pour" "post" "pond" "poem" "pomp" "poll" "polo" "pole"
   "pull" "pump" "push" "pout" "pore" "port" "pose" "pour"
   "supply" "study" "story" "stamp" "style" "spend"
   "might" "music" "money" "mouth" "model" "month"
   "party" "point" "place" "plant" "power" "price" "prime"
   "pretty" "player" "played" "plenty" "prompt" "proper"
   "simple" "system" "summer" "stream" "symbol" "spread"
   "policy" "permit" "person" "period" "simply" "supply"
   "mystery" "primary" "payment" "popular" "produce" "project"
   "campus" "column" "comedy" "comply" "crispy" "custom"
   "demand" "deploy" "detail" "domain" "dynamic" "display"
   "employ" "employ" "empire" "example" "expand" "explain"
   "impact" "include" "income" "indeed" "module" "moment"
   "media" "medium" "memory" "method" "motive" "sample"
   "product" "payment" "protein" "outcome" "options" "permit"
   "purpose" "respect" "support" "special" "sponsor" "typical"

   ;; ---- level-4 adds (b w f g) -------------------------------------------
   "be" "by" "go" "if" "of" "off" "big" "bag" "bad" "ban"
   "bar" "bat" "bed" "bet" "bid" "bit" "bog" "bow" "box"
   "bug" "bus" "but" "buy" "bye" "beg" "bob" "bog" "beg"
   "fan" "far" "fat" "few" "fig" "fin" "fit" "fly" "fog"
   "for" "fox" "fun" "fur" "fab" "fad" "fax" "fib" "fig"
   "gap" "gas" "gel" "gem" "gig" "gin" "god" "got" "gum"
   "gun" "gut" "guy" "gag" "gal" "gal" "gob" "gob"
   "war" "was" "web" "wet" "wig" "win" "wit" "wow" "wax"
   "way" "win" "won" "woe" "woo" "who" "why" "owe"
   "base" "best" "bill" "bird" "bite" "blow" "blue" "body"
   "bold" "bond" "bone" "book" "boom" "both" "brief" "bring"
   "built" "bulk" "burn" "back" "band" "bank" "beef" "been"
   "born" "boss" "bent" "berg" "bind" "brow"
   "face" "fact" "fail" "fall" "farm" "fast" "feel" "file"
   "fill" "film" "find" "fine" "fire" "firm" "flag" "flat"
   "flew" "flow" "fold" "font" "food" "foot" "fore" "form"
   "fort" "four" "free" "from" "fuel" "full" "fund" "fuse"
   "gave" "give" "glad" "glow" "goal" "gold" "good" "grab"
   "gram" "grew" "grid" "grin" "grip" "grow" "gulf" "gust"
   "wake" "walk" "wall" "want" "ward" "warm" "worn" "wrap"
   "wide" "wife" "wild" "will" "wind" "wine" "wise" "with"
   "woke" "wolf" "word" "work" "worm" "writ"
   "about" "above" "after" "again" "being" "below" "brief"
   "bring" "built" "being" "below" "brief" "before"
   "every" "field" "first" "found" "given" "going" "great"
   "group" "guide" "their" "while" "world" "would" "write"
   "badly" "basic" "began" "begin" "being" "below" "black"
   "blame" "blank" "blast" "blend" "block" "blood" "board"
   "boast" "brain" "brand" "brave" "break" "breed" "broad"
   "brown" "build" "burst"
   "fancy" "fewer" "fiber" "fixed" "flame" "fleet" "flesh"
   "fluid" "forum" "found" "frame" "fresh" "front" "froze"
   "funny" "flair" "flown" "flock" "floor" "flour" "given"
   "globe" "gloom" "gloss" "glove" "grace" "grade" "grant"
   "grasp" "grave" "greet" "grief" "grind" "groan" "grove"
   "guard" "guest" "guide" "built" "burst" "began"
   "water" "where" "which" "whole" "woman" "women" "works"
   "worry" "worth" "wrong" "write" "wrote"
   "follow" "forget" "format" "fourth" "garden" "gather"
   "gender" "gifted" "global" "ground" "growth" "guitar"
   "budget" "bridge" "broken" "burden" "button" "fabric"
   "factor" "failed" "family" "finger" "finish" "flight"
   "formal" "forward" "figure" "filter" "behind" "belong"
   "border" "bottle" "bottom" "bought" "branch" "breath"
   "weight" "window" "winner" "winter" "wonder" "wooden"
   "worker" "worthy" "warmth"
   "freedom" "forever" "forward" "formula" "fragile" "federal"
   "general" "grocery" "benefit" "binding" "biology" "blossom"
   "border" "brother" "building" "bulletin" "burden" "between"
   "factory" "fantasy" "feature" "feeling" "finding" "fishing"
   "fitting" "flowing" "focused" "folding" "foreign" "forming"
   "writing" "wanting" "warming" "wearing" "winning" "wishing"
   "working" "worthy" "worried"

   ;; ---- level-5 adds (k v j x) -------------------------------------------
   "ax" "ox" "fix" "hex" "mix" "six" "tax" "vex" "wax"
   "jay" "jet" "jab" "jag" "jam" "jar" "jaw" "joy" "jot"
   "jug" "jut" "job" "jog"
   "key" "kid" "kit" "kin" "keg" "ken" "knit" "knob"
   "van" "vat" "via" "vow" "vim" "vex" "vet" "via" "vie"
   "axis" "apex" "exam" "exit" "flux" "jinx" "kerb" "knee"
   "jack" "jade" "jest" "join" "joke" "jolt" "jump" "just"
   "keen" "keep" "kill" "kind" "king" "kiss" "knew" "know"
   "vain" "vale" "veil" "vein" "very" "vest" "view" "vine"
   "void" "vote" "vast" "vase" "vary"
   "exact" "excel" "exist" "extra" "index" "inbox" "maker"
   "jacket" "jargon" "joyful" "jumped" "junior" "justify"
   "kernel" "kingdom" "kitchen"
   "value" "valid" "valve" "video" "vigor" "viral" "visit"
   "vocal" "voice" "vital" "vivid" "vision" "visual"
   "mixed" "pixel" "proxy" "relax" "toxic" "sixty"
   "expert" "exotic" "exempt" "extend" "extreme" "examine"
   "invoke" "involve" "justify" "keeping" "knowing" "looking"
   "making" "moving" "taking" "talking" "thinking" "working"
   "boxing" "fixing" "mixing" "taxing" "vexing" "joking"

   ;; ---- level-6 adds (' , . ;) -------------------------------------------
   "it's" "isn't" "aren't" "don't" "can't" "won't" "wasn't"
   "didn't" "doesn't" "hadn't" "hasn't" "haven't" "shouldn't"
   "wouldn't" "couldn't" "mustn't" "needn't"
   "I'm" "I've" "I'll" "I'd" "you're" "you've" "you'll"
   "he's" "she's" "it's" "we're" "we've" "we'll" "we'd"
   "they're" "they've" "they'll" "they'd"
   "that's" "there's" "here's" "what's" "who's" "how's"
   "let's" "she'd" "he'd" "you'd" "one's" "man's" "day's"
   "good." "fine." "done." "nice." "sure." "right." "great."
   "yes." "no." "ok." "hi." "hello." "thanks." "sorry."
   "well," "then," "also," "next," "first," "second," "third,"
   "again," "often," "maybe," "never," "always,"
   "hello;" "finally;" "however;" "therefore;" "meanwhile;"

   ;; ---- level-7 / all-letters supplemental words -------------------------
   "quiz" "quip" "aqua" "queue" "quick" "quiet" "quite" "quote"
   "zero" "zinc" "zone" "zoom" "zeal" "zest" "zippy"
   "fizz" "fuzz" "buzz" "jazz" "razz" "pizzazz"
   "acknowledge" "approximately" "beautiful" "catastrophe"
   "development" "environment" "exceptional" "functionality"
   "government" "horizontal" "immediately" "jurisdiction"
   "knowledge" "laboratory" "manufacture" "nevertheless"
   "opportunity" "perspective" "qualification" "revolutionary"
   "substantially" "technological" "uncomfortable" "voluntarily"
   "approximately" "extraordinary" "sophisticated"])

;; Deduplicate the master list, keeping order
(def word-list
  (vec (distinct master-list)))

;; ---------------------------------------------------------------------------
;; Sentence corpus for level 8 (sentence mode)
;; ---------------------------------------------------------------------------

(def sentences
  ["The quick brown fox jumps over the lazy dog."
   "She sells seashells by the seashore."
   "How much wood would a woodchuck chuck?"
   "Peter Piper picked a peck of pickled peppers."
   "The rain in Spain stays mainly in the plain."
   "To be or not to be, that is the question."
   "All that glitters is not gold."
   "Actions speak louder than words."
   "Every cloud has a silver lining."
   "The early bird catches the worm."
   "A rolling stone gathers no moss."
   "Fortune favours the bold and the brave."
   "Knowledge is power and wisdom its fruit."
   "The best time to plant a tree was twenty years ago."
   "In the middle of difficulty lies opportunity."
   "Great things are done by a series of small things."
   "The only way to do great work is to love what you do."
   "It does not matter how slowly you go so long as you do not stop."
   "Success is not final, failure is not fatal."
   "The journey of a thousand miles begins with a single step."])

;; ---------------------------------------------------------------------------
;; Filtering helpers
;; ---------------------------------------------------------------------------

(defn- word-chars
  "Returns the set of distinct lowercase non-space characters in a word."
  [w]
  (into #{} (map str (filter #(not= % \space) (clojure.string/lower-case w)))))

(defn- all-chars-allowed?
  "True if every character in word is in the allowed set."
  [word allowed-set]
  (every? #(contains? allowed-set %) (word-chars word)))

(defn- contains-new-letter?
  "True if word contains at least one letter from new-letters."
  [word new-letters]
  (some #(contains? new-letters %) (word-chars word)))

(defn filter-words-for-level
  "Return the subset of words usable at the given level.
   - allowed-set  : cumulative letters available (nil = all)
   - new-letters  : letters introduced at this specific level (nil = all)
   - allow-caps?  : if false, drop words containing uppercase characters
   Words must use only allowed characters AND contain at least one new-letter."
  [words level allow-caps?]
  (let [allowed   (level-letters level)
        new-lets  (when (< level 7) (level-new-letters level))]
    (cond->> words
      ;; drop capitalised words unless caps are allowed
      (not allow-caps?)
      (filter #(= % (clojure.string/lower-case %)))

      ;; drop words that use letters not yet unlocked
      (some? allowed)
      (filter #(all-chars-allowed? % allowed))

      ;; require at least one letter from the current level's new set
      (some? new-lets)
      (filter #(contains-new-letter? % new-lets)))))

;; ---------------------------------------------------------------------------
;; Public API
;; ---------------------------------------------------------------------------

(defn words-for-round
  "Return a lazy seq of `n` random words suitable for `level`.
   Words are sampled with replacement from the filtered list.
   Falls back to the level-letter string itself if no words match."
  [level n allow-caps?]
  (let [pool (filter-words-for-level word-list level allow-caps?)]
    (if (seq pool)
      (let [v (vec pool)]
        (repeatedly n #(rand-nth v)))
      ;; fallback: emit the level's letters as a word
      (let [fallback (apply str (sort (level-new-letters level)))]
        (repeat n fallback)))))

(defn sentences-for-round
  "Return a lazy seq of `n` sentences sampled from the sentence corpus."
  [n]
  (repeatedly n #(rand-nth sentences)))
