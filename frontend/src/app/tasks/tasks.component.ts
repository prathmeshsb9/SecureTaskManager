import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { TaskService, Task } from './task.service';
import { AuthService } from '../auth/auth.service';

import { NavbarComponent } from '../layout/navbar/navbar.component';


@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  tasks: Task[] = [];

  title = '';
  description = '';

  //  Edit mode state
  editingTaskId: number | null = null;
  editTitle = '';
  editDescription = '';

  constructor(
    private taskService: TaskService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getTasks().subscribe(tasks => {
      this.tasks = tasks;
    });
  }

  // CREATE TASK 
  addTask() {
  if (!this.title || !this.description) return;

  const task = {
    title: this.title,
    description: this.description
  };

  this.taskService.createTask(task).subscribe(newTask => {
    this.tasks.push(newTask); 
    this.title = '';
    this.description = '';
  });
}

  //  ENTER EDIT MODE
  startEdit(task: Task) {
    this.editingTaskId = task.id!;
    this.editTitle = task.title;
    this.editDescription = task.description;
  }

  // UPDATE TASK 
  updateTask() {
    if (!this.editingTaskId) return;

    const updatedTask: Task = {
      title: this.editTitle,
      description: this.editDescription
    };

    this.taskService.updateTask(this.editingTaskId, updatedTask)
      .subscribe((savedTask) => {

        const index = this.tasks.findIndex(
          task => task.id === this.editingTaskId
        );

        if (index !== -1) {
          this.tasks[index] = {
            ...this.tasks[index],
            ...savedTask
          };
        }

        this.cancelEdit();
      });
  }

  // CANCEL EDIT
  cancelEdit() {
    this.editingTaskId = null;
    this.editTitle = '';
    this.editDescription = '';
  }

  // DELETE TASK 
  deleteTask(id?: number) {
    if (!id) return;

    this.taskService.deleteTask(id).subscribe(() => {
      this.tasks = this.tasks.filter(task => task.id !== id);
    });
  }

  
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
