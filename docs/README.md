# UrMum User Guide

This is UrMum, your friendly personal task manager chatbot!
You can use UrMum to keep track of todos, deadlines, and events from the command line.

---

## Getting Started

1. Run the jar file.
2. Type commands into the app.
3. Use bye to exit.

Tasks are automatically saved to `./data/six.txt`.

---

## Features & Commands

### Add a Todo

Add a simple task with no date/time.

Format: `todo <description>`

Example:

```
todo read book
```

### Add a Deadline

Add a task that must be done by a specific date/time.

Format: `deadline <description> /by <yyyy-MM-dd HHmm>`

Example:

```
deadline submit report /by 2026-03-01 2359
```

### Add an Event

Add a task that occurs during a specific period.

Format: `event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`

Example:

```
event project meeting /from 2026-03-02 1400 /to 2026-03-02 1600
```

### List All Tasks

See all your current tasks.

Format:

```
bye
```

### Mark/Unmark a Task as Done/Not Done

Add a simple task with no date/time.

Mark a task as done: `mark <task number>`

Unmark a task: `unmark <task number>`

Example:

```
mark 2
unmark 2
```

### Delete a Task

Remove a task from your list.

Format: `delete <task number>`

Example:

```
delete 3
```

### Find Tasks by Keyword

Search for tasks containing a keyword.

Format: `find <keyword>`

Example:

```
find book
```

### Undo Last Change

Undo your most recent add, delete, mark, or unmark command.

Format:

```
undo
```

### Exit the Chatbot

Say goodbye and close UrMum.

Format:

```
bye
```

---

## Error Handling Notes

- If a command is not recognized, UrMum will notify you with an error message.
- Missing required parameters will show an error message explaining what is needed.
- Invalid task numbers (e.g. 0, negative, out of range, or non-integers) are rejected.
- Invalid date/time formats for deadlines/events are rejected; UrMum will prompt you to use the correct format (`yyyy-MM-dd HHmm`).
- If you try to undo when there is nothing to undo, UrMum will let you know.
- All errors are explained clearly so you can fix your input and try again.

---

Enjoy using UrMum to organize your life!
