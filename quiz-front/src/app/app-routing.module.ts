import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { QuestionListComponent } from './components/question/question-list/question-list.component';
import { LoginComponent } from './components/login/login.component';
import { UsersListComponent } from './components/user/users-list/users-list.component';
import { ExamsListComponent } from './components/exam/exams-list/exams-list.component';
import { UserFormComponent } from './components/user/user-form/user-form.component';
import { UserDetailComponent } from './components/user/user-detail/user-detail.component';
import { TopicListComponent } from './components/topic/topic-list/topic-list.component';
import { TopicFormComponent } from './components/topic/topic-form/topic-form.component';
import { TopicDetailComponent } from './components/topic/topic-detail/topic-detail.component';
import { QuestionFormComponent } from './components/question/question-form/question-form.component';
import { QuestionDetailComponent } from './components/question/question-detail/question-detail.component';
import { ExamFormComponent } from './components/exam/exam-form/exam-form.component';
import { ExamDetailComponent } from './components/exam/exam-detail/exam-detail.component';
import { QuestionSearchComponent } from './components/question/question-search/question-search.component';
import { TakeExamComponent } from './components/exam/take-exam/take-exam.component';
import { SubmissionListComponent } from './components/submission/submission-list/submission-list.component';
import { SubmissionDetailComponent } from './components/submission/submission-detail/submission-detail.component';
import { AuthGuard } from './auth.guard';

export const routes: Routes = [
  { path: 'questions', component: QuestionSearchComponent, canActivate: [AuthGuard] },
  { path: 'questionCreate', component: QuestionFormComponent, canActivate: [AuthGuard] },
  { path: 'questionDetail/:id', component: QuestionDetailComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UsersListComponent, canActivate: [AuthGuard] },
  { path: 'userCreate', component: UserFormComponent},
  { path: 'userDetail/:id', component: UserDetailComponent, canActivate: [AuthGuard] },
  { path: 'topics', component: TopicListComponent, canActivate: [AuthGuard] },
  { path: 'topicCreate', component: TopicFormComponent, canActivate: [AuthGuard] },
  { path: 'topicDetail/:id', component: TopicDetailComponent, canActivate: [AuthGuard] },
  { path: 'exams', component: ExamsListComponent, canActivate: [AuthGuard] },
  { path: 'examDetail/:id', component: ExamDetailComponent, canActivate: [AuthGuard] },
  { path: 'examCreate', component: ExamFormComponent, canActivate: [AuthGuard] },
  { path: 'takeExam/:id', component: TakeExamComponent, canActivate: [AuthGuard] },
  { path: 'submissions', component: SubmissionListComponent, canActivate: [AuthGuard] },
  { path: 'submissionDetail/:id', component: SubmissionDetailComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
