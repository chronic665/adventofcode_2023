#!/usr/bin/sbcl --script

(defvar linefirst)
(defvar linelast)
(write-line "Hello, World!")


(defun getvalue (char1 char2)
    "Extract the sum"
    (+ char1 char2)
)

(defun linevalue (line)
    "Calculate line value"
    (write-line "Test")
    (setq linefirst (subseq line 0 1))
    (write-line linefirst)
    (write-line "Test2")
    (setq linelast (subseq line (-(length line) 1)))
    (write-line "Test3")
    (write-line linelast)
    (write
        (getvalue
            ; (digit-char-p (string-to-char linefirst))
            ; (digit-char-p (string-to-char linelast))

            (parse-integer linefirst)
            (parse-integer linelast)
        )
    )
)


(linevalue "1abc2e")