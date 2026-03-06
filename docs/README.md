# Friday User Guide

Friday is a command-line chatbot that helps you manage tasks quickly and efficiently.  
You can add todos, deadlines, and events, mark tasks as done, and view your task list — all through simple text commands.

---

## Viewing the Task List

Displays all tasks currently stored by Friday.

Example:
`list`

Expected output:
```
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] submit assignment (by: 2026-03-10)
```

---

## Adding a Todo

Adds a simple task without any date or time.

Example:
`todo read book`
```
Expected output:
Got it. I've added this task:
[T][ ] read book
Now you have 1 task in the list.
```

---

## Adding a Deadline

Adds a task that must be completed before a specific date.

Example:
`deadline submit assignment /by 2026-03-10`

Expected output:
```
Got it. I've added this task:
[D][ ] submit assignment (by: 2026-03-10)
Now you have 2 tasks in the list.
```

---

## Adding an Event

Adds a task that occurs at a specific time.

Example:
`event project meeting /at Monday 2pm`

Expected output:
```
Got it. I've added this task:
[E][ ] project meeting (at: Monday 2pm)
Now you have 3 tasks in the list.
```

---

## Marking a Task as Done

Marks a task in the list as completed.

Example:
`mark 1`

Expected output:
```
Nice! I've marked this task as done:
[T][X] read book
```

---

## Unmarking a Task

Marks a completed task as not done.

Example:
`unmark 1`

Expected output:
```
OK, I've marked this task as not done yet:
[T][ ] read book
```

---

## Finding tasks

Finds tasks whose description contains a given keyword.

Example: `find book`

Expected output:
```
Here are the matching tasks in your list:
1. [T][ ] read book
2. [D][ ] return book (by: 2026-03-12)
```

---

## Error Handling

Friday provides helpful error messages if a command is used incorrectly.  
For example:
```
OOPS!!! The description of a todo cannot be empty.
```


---

## Command Summary

| Command | Description |
|-------|-------------|
| `list` | Shows all tasks |
| `todo <description>` | Adds a todo task |
| `deadline <description> /by <date>` | Adds a deadline task |
| `event <description> /at <time>` | Adds an event |
| `mark <task number>` | Marks a task as done |
| `unmark <task number>` | Marks a task as not done |

---

## Notes

- Task numbers correspond to the index shown in the `list` command.
- Commands are case-insensitive.
- Ensure the correct format when adding deadlines or events.
