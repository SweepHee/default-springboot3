package com.example.demo.controller

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Int> {

}